package com.projectsakura.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsList: List<News> = emptyList() // Initialize with empty list

    @SuppressLint("NotifyDataSetChanged")
    fun updateNewsList(newList: List<News>) {
        // Filter out articles without images before setting data
        val filteredList = newList.filter { it.urlToImage?.isNotEmpty() == true }
        newsList = filteredList
        notifyDataSetChanged() // Update adapter after data change
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]

        // Log data for debugging (optional)
        //Log.d("NewsAdapter", "News title at position $position: ${news.title}")
        Log.d("NewsAdapter", "News content at position $position: ${news.content}")

        // Null check before setting title
        holder.titleTextView.text = news.title.trim() ?: "No Title Available"

        // Null check before setting title
        holder.description.text = news.description.trim() ?: "No Title Available"

        // Use Glide to load image if available
        if (news.urlToImage.isNotEmpty()) {
            Glide.with(context).load(news.urlToImage).into(holder.imageView)
        } else {
            // Handle case where image URL is empty (e.g., display placeholder text)
            holder.imageView.setImageResource(R.drawable.ic_no_image) // Placeholder image
        }

        // Click listener for the entire item layout
        holder.itemView.setOnClickListener {
            val url = news.url
            val content = news.content // Retrieve the content from the News object
            // Launch the NewsDetailActivity with the content passed as an extra
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("newsUrl", url)
            intent.putExtra("newsTitle", news.title)
            intent.putExtra("newsContent", content) // Pass the content data
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = newsList.size
}
