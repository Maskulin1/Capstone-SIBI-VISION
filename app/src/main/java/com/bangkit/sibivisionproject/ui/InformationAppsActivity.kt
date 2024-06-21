package com.bangkit.sibivisionproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.sibivisionproject.databinding.ActivityInformationAppsBinding

class InformationAppsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInformationAppsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvInformation.text = "SIBI Vision: An Innovative Application to Connect and Assist the Indonesian Sign Language (SIBI) Community in Healthcare. SIBI Vision addresses the communication gap faced by the Indonesian Sign Language (SIBI) community in accessing healthcare services. Despite advancements in technology, real-time translation of SIBI remains a challenge, hindering effective communication between healthcare providers and SIBI users. Our project aims to develop an innovative computer vision application that seamlessly translates SIBI gestures into text or spoken language in real-time."
    }
}
