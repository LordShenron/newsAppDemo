package com.projectsakura.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    private var newsUrl: String? = null

    private val contentCache = mutableMapOf<String, String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_news_detail)

        val content = intent.getStringExtra("newsContent")
        val title = intent.getStringExtra("newsTitle")
        newsUrl = intent.getStringExtra("newsUrl")

        val uniqueKey = content?.hashCode()?.toString() ?: ""

        val contentTextView = findViewById<TextView>(R.id.news_content_text_view)

        if (content != null) {
            if (contentCache.containsKey(uniqueKey)) {
                // Display cached content
                Log.d("NewsDetailActivity", "Content fetched from cache for unique key: $uniqueKey")
                contentTextView.text = contentCache[uniqueKey]
            } else {
                // Display content fetched from intent
                Log.d("NewsDetailActivity", "Content fetched from intent for unique key: $uniqueKey")
                contentTextView.text = content
                // Cache content
                contentCache[uniqueKey] = content
                Log.d("NewsDetailActivity", "Content cached for unique key: $uniqueKey")
            }
            contentTextView.visibility = View.VISIBLE
        } else {
            contentTextView.visibility = View.GONE
            val noContentTextView = findViewById<TextView>(R.id.no_content_text_view)
            noContentTextView.visibility = View.VISIBLE
            noContentTextView.text = getString(R.string.content_not_available)
        }

        if (title != null) {
            supportActionBar?.title = title
        }

        val readMoreTextView = findViewById<TextView>(R.id.read_more_text_view)
        readMoreTextView.setOnClickListener {
            openFullArticle()
        }
    }

    private fun openFullArticle() {
        newsUrl?.let { url ->
            Log.d("NewsDetailActivity", "Opening full article: $url")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
