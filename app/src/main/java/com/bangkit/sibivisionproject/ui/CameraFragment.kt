package com.bangkit.sibivisionproject.ui

import android.os.Bundle
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bangkit.sibivisionproject.databinding.FragmentCameraBinding
import com.bangkit.sibivisionproject.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()

        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .setTargetResolution(Size(640, 480))
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(640, 480))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, YourImageAnalyzer(binding.tvTranslationResult))
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )
            } catch (exc: Exception) {
                // Log error
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private class YourImageAnalyzer(private val resultView: TextView) : ImageAnalysis.Analyzer {

        override fun analyze(imageProxy: ImageProxy) {
            val buffer = imageProxy.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)

            // Assuming the input size for the model is 84
            val inputSize = 84
            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * inputSize)
            byteBuffer.put(bytes)

            val model = Model.newInstance(resultView.context)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, inputSize), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val result = outputFeature0.floatArray
            // Process result
            resultView.text = "Translation: ${result.joinToString()}"

            model.close()
            imageProxy.close()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cameraExecutor.shutdown()
    }
}



//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.bangkit.sibivisionproject.databinding.FragmentCameraBinding
//import com.bangkit.sibivisionproject.ui.TranslationResultActivity
//import java.text.SimpleDateFormat
//import java.util.*
//
//class CameraFragment : Fragment() {
//
//    private var _binding: FragmentCameraBinding? = null
//    private val binding get() = _binding!!
//
//    private val REQUEST_VIDEO_CAPTURE = 1
//    private val PICK_VIDEO_REQUEST = 2
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentCameraBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.btnRecord.setOnClickListener {
//            dispatchTakeVideoIntent()
//        }
//
//        binding.btnGallery.setOnClickListener {
//            dispatchPickVideoIntent()
//        }
//    }
//
//    private fun dispatchTakeVideoIntent() {
//        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//        if (takeVideoIntent.resolveActivity(requireActivity().packageManager) != null) {
//            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
//        }
//    }
//
//    private fun dispatchPickVideoIntent() {
//        val pickVideoIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(pickVideoIntent, PICK_VIDEO_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            val videoUri: Uri? = data?.data
//            if (videoUri != null) {
//                // Process the video for translation
//                val translationResult = "Sample translation text"
//                navigateToTranslationResult(translationResult)
//            }
//        }
//    }
//
//    private fun navigateToTranslationResult(translation: String) {
//        val intent = Intent(activity, TranslationResultActivity::class.java).apply {
//            putExtra("translation_result", translation)
//        }
//        startActivity(intent)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
