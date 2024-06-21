package com.bangkit.sibivisionproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.sibivisionproject.R

class NewsAdapter(private val newsList : ArrayList<NewsData>, val listener: (NewsData) -> Unit)
    : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(newsList[position],listener)

        val currentItem = newsList[position]
        holder.dataImage.setImageResource(currentItem.dataImage)
        holder.dataTitle.text = currentItem.dataTitle
        itemCount

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dataImage : ImageView = itemView.findViewById(R.id.img_item_photo)
        val dataTitle : TextView = itemView.findViewById(R.id.tv_item_name)

        fun bindView(NewsData: NewsData, listener: (NewsData) -> Unit) {
            dataImage.setImageResource(NewsData.dataImage)
            dataTitle.text = NewsData.dataTitle
            itemView.setOnClickListener{
                listener(NewsData)
            }
        }
    }
}
