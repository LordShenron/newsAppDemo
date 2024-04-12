package com.projectsakura.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_news_detail)

        val content = intent.getStringExtra("newsContent") // Assuming content is passed as "newsContent"
        val title = intent.getStringExtra("newsTitle") // Optional: Retrieve title

        val contentTextView = findViewById<TextView>(R.id.news_content_text_view) // Assuming you have a TextView with ID "news_content_text_view"

        if (content != null) {
            // You might need to format the content for display (optional)
            contentTextView.text = content
            contentTextView.visibility = View.VISIBLE
        } else {
            // Handle case where content is not available
            contentTextView.visibility = View.GONE // Hide TextView if content is missing
            val noContentTextView = findViewById<TextView>(R.id.no_content_text_view) // Assuming you have a TextView with ID "no_content_text_view"
            noContentTextView.visibility = View.VISIBLE
            noContentTextView.text = "Article content not available." // Set appropriate message
        }

        // Optional: Set title based on received title (if available)
        if (title != null) {
            supportActionBar?.title = title
        }
    }
}

