package com.bangkit.sibivisionproject.ui

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.sibivisionproject.R
import com.bangkit.sibivisionproject.adapter.NewsAdapter
import com.bangkit.sibivisionproject.adapter.NewsData

class HomeFragment : Fragment() {
    companion object {
        val INTENT_PARCELABLE = "OBJECT_INTENT"
    }

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList: ArrayList<NewsData>

    lateinit var image: Array<Int>
    lateinit var title: Array<String>
    lateinit var descriptions: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerview_news)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = NewsAdapter(newsArrayList){
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra(HomeFragment.INTENT_PARCELABLE, it)
            startActivity(intent)
        }
    }
    private fun dataInitialize() {
        newsArrayList = arrayListOf<NewsData>()

        image = arrayOf(
            R.drawable.bisindo1,
            R.drawable.bisindo2,
            R.drawable.bisindo3,
            R.drawable.bisindo4,
            R.drawable.bisindo5,
            R.drawable.bisindo6,
            R.drawable.bisindo7,
            R.drawable.bisindo8,
            R.drawable.bisindo9,
            R.drawable.bisindo10
        )
        title = arrayOf(
            getString(R.string.title1),
            getString(R.string.title2),
            getString(R.string.title3),
            getString(R.string.title4),
            getString(R.string.title5),
            getString(R.string.title6),
            getString(R.string.title7),
            getString(R.string.title8),
            getString(R.string.title9),
            getString(R.string.title10)
        )
        descriptions = arrayOf(
            getString(R.string.bisindo11),
            getString(R.string.bisindo21),
            getString(R.string.bisindo31),
            getString(R.string.bisindo41),
            getString(R.string.bisindo51),
            getString(R.string.bisindo61),
            getString(R.string.bisindo71),
            getString(R.string.bisindo81),
            getString(R.string.bisindo91),
            getString(R.string.bisindo101)
            )

        for (i in image.indices) {

            val news = NewsData(image[i],title[i], descriptions[i])
            newsArrayList.add(news)
        }
    }
}