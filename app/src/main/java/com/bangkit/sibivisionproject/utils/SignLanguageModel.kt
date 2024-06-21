package com.bangkit.sibivisionproject.utils

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class SignLanguageModel(private val context: Context) {
    private lateinit var interpreter: Interpreter

    init {
        loadModel()
    }

    private fun loadModel() {
        val modelFileDescriptor = context.assets.openFd("model.tflite")
        val inputStream = FileInputStream(modelFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = modelFileDescriptor.startOffset
        val declaredLength = modelFileDescriptor.declaredLength
        val model = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        val options = Interpreter.Options()
        interpreter = Interpreter(model, options)
    }

    fun detectSignLanguage(bitmap: Bitmap): String {
        // Preprocess the image to the correct format for the model
        val input = preprocess(bitmap)
        val output = Array(1) { FloatArray(1) }
        interpreter.run(input, output)
        return output[0][0].toString()
    }

    private fun preprocess(bitmap: Bitmap): ByteBuffer {
        val inputSize = 224  // Assuming input size for the model is 224x224
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
        val intValues = IntArray(inputSize * inputSize)
        scaledBitmap.getPixels(intValues, 0, scaledBitmap.width, 0, 0, scaledBitmap.width, scaledBitmap.height)
        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val value = intValues[pixel++]
                byteBuffer.putFloat(((value shr 16 and 0xFF) - 127.5f) / 127.5f)
                byteBuffer.putFloat(((value shr 8 and 0xFF) - 127.5f) / 127.5f)
                byteBuffer.putFloat(((value and 0xFF) - 127.5f) / 127.5f)
            }
        }
        return byteBuffer
    }
}
