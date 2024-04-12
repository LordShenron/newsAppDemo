package com.projectsakura.newsapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.news_title)
    val imageView: ImageView = itemView.findViewById(R.id.news_image)
    val description: TextView = itemView.findViewById(R.id.news_description)
}
