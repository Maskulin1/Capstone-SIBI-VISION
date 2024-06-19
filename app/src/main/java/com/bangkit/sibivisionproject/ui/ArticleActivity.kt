package com.bangkit.sibivisionproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bangkit.sibivisionproject.R
import com.bangkit.sibivisionproject.adapter.NewsData

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val NewsData = intent.getParcelableExtra<NewsData>(HomeFragment.INTENT_PARCELABLE)

        val imageNews: ImageView = findViewById(R.id.imageView)
        val titleNews: TextView = findViewById(R.id.titleView)
        val descriptionNews: TextView = findViewById(R.id.tv_description23)

        imageNews.setImageResource(NewsData?.dataImage!!)
        titleNews.text = NewsData.dataTitle
        descriptionNews.text = NewsData.dataDescription

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
