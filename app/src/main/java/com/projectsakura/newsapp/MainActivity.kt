package com.projectsakura.newsapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var newsList: RecyclerView
    private lateinit var adapter: NewsAdapter
    private lateinit var emptyView: View

    private val contentCache = mutableMapOf<String, List<News>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.news_list)
        newsList.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this)
        newsList.adapter = adapter

        emptyView = findViewById(R.id.empty_view)

        if (contentCache.isNotEmpty()) {
            // Display cached content if available
            adapter.updateNewsList(contentCache.values.flatten())
            emptyView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        } else {
            fetchNews()
        }
    }

    private fun fetchNews() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NewsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getTopHeadlines("in", "2e14de5a6c214d15a3c1c46224dd0c48")
                val news = response.articles
                runOnUiThread {
                    adapter.updateNewsList(news)
                    emptyView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                    // Cache the fetched news
                    contentCache["topHeadlines"] = news
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error fetching news!", Toast.LENGTH_SHORT).show()
                    emptyView.visibility = View.VISIBLE
                }
            }
        }
    }
}
