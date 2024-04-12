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
    private lateinit var adapter: NewsAdapter // Initialize adapter outside onCreate
    private lateinit var emptyView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.news_list)
        newsList.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this) // Initialize adapter with context
        newsList.adapter = adapter // Set adapter after initialization

        emptyView = findViewById(R.id.empty_view)

        // Retrofit setup with coroutines
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(NewsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getTopHeadlines("in", "2e14de5a6c214d15a3c1c46224dd0c48") // Replace with your API key
                val news = response.articles // Assuming "articles" key holds the list
                runOnUiThread {
                    adapter.updateNewsList(news)
                    emptyView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                }
            } catch (e: Exception) {
                runOnUiThread {
                    // Handle API call failure
                    Toast.makeText(this@MainActivity, "Error fetching news!", Toast.LENGTH_SHORT).show()
                    emptyView.visibility = View.VISIBLE // Show empty view if API call fails
                }
            }
        }
    }
}
