package com.bangkit.sibivisionproject.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sibivisionproject.databinding.ActivityTranslationResultBinding

class TranslationResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTranslationResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslationResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val translationResult = intent.getStringExtra("translation_result") ?: "No result"
        binding.tvResult.text = translationResult

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
