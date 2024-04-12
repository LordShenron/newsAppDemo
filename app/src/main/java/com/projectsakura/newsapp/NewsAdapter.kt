package com.projectsakura.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsList: List<News> = emptyList() // Initialize with empty list
    private val contentCache = mutableMapOf<String, String?>() // Map to store fetched content

    // Function to fetch full article content using Jsoup
    private suspend fun fetchFullArticleContent(url: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val doc: Document = Jsoup.connect(url).get()

                // Find the element(s) containing the article content based on the HTML structure
                val articleContent = doc.select("p") // Adjust selector based on the structure of the website

                // Extract the text content from the selected element(s)
                val content = StringBuilder()
                for (element in articleContent) {
                    content.append(element.text()).append("\n")
                }

                // Return the extracted article content
                content.toString().trim()
            } catch (e: Exception) {
                Log.e("NewsAdapter", "Error fetching article content", e)
                null
            }
        }
    }

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

        holder.itemView.setOnClickListener {
            val url = news.url

            // Check if content is cached
            val cachedContent = contentCache[url]
            if (cachedContent != null) {
                // If content is cached, open NewsDetailActivity with cached content
                openNewsDetailActivity(url, news.title, cachedContent)
            } else {
                // If content is not cached, fetch and cache the content
                CoroutineScope(Dispatchers.Main).launch {
                    val content = fetchFullArticleContent(url)
                    content?.let {
                        // Cache the fetched content
                        contentCache[url] = it
                        openNewsDetailActivity(url, news.title, it)
                    }
                }
            }
        }

        holder.titleTextView.text = news.title.trim() ?: "No Title Available"
        holder.description.text = news.description.trim() ?: "No Description Available"

        if (news.urlToImage.isNotEmpty()) {
            Glide.with(context).load(news.urlToImage).into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.ic_no_image) // Placeholder image
        }
    }

    override fun getItemCount(): Int = newsList.size

    private fun openNewsDetailActivity(url: String, title: String?, content: String) {
        val intent = Intent(context, NewsDetailActivity::class.java)
        intent.putExtra("newsUrl", url)
        intent.putExtra("newsTitle", title)
        intent.putExtra("newsContent", content)
        context.startActivity(intent)
    }
}
