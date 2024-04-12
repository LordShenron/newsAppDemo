package com.projectsakura.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView

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
        Log.d("NewsAdapter", "News title at position $position: ${news.title}")
        Log.d("NewsAdapter", "News url at position $position: ${news.urlToImage}")

        // Null check before setting title
        holder.titleTextView.text = news.title?.let {
            it.trim() // Optional transformation (e.g., trimming)
        } ?: "No Title Available"

        // Null check before setting title
        holder.description.text = news.title?.let {
            it.trim() // Optional transformation (e.g., trimming)
        } ?: "No Title Available"

        // Use Glide to load image if available
        if (news.urlToImage.isNotEmpty()) {
            Glide.with(context).load(news.urlToImage).into(holder.imageView)
        } else {
            // Handle case where image URL is empty (e.g., display placeholder text)
            holder.imageView.setImageResource(R.drawable.ic_no_image) // Placeholder image
        }


        // Click listener to open article URL in WebView
        holder.itemView.setOnClickListener {
            val url = news.url // Use null-safe navigation to access url
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } else {
                // Handle case where url is null (e.g., display a toast message)
                Toast.makeText(context, "Article URL unavailable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = newsList.size
}
