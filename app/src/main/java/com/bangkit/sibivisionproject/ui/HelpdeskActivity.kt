package com.bangkit.sibivisionproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sibivisionproject.databinding.ActivityHelpdeskBinding

class HelpdeskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpdeskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpdeskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHelpdesk.text = """
            1. Experience your customers' problems.
            2. Be clear on your article's objective.
            3. Write what you know.
            4. Focus on your customers' goals, not your features.
            5. Make your customers feel smart.
            6. Use your customers' words.
        """.trimIndent()
    }
}
