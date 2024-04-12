package com.projectsakura.newsapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_news_detail)

        val url = intent.getStringExtra("newsUrl")
        val title = intent.getStringExtra("newsTitle")
        val content = intent.getStringExtra("newsContent")

        val contentTextView = findViewById<TextView>(R.id.news_content_text_view)

        if (content != null) {
            contentTextView.text = content
            contentTextView.visibility = View.VISIBLE
        } else {
            contentTextView.visibility = View.GONE
            val noContentTextView = findViewById<TextView>(R.id.no_content_text_view)
            noContentTextView.visibility = View.VISIBLE
            noContentTextView.text = "Article content not available."
        }

        if (title != null) {
            supportActionBar?.title = title
        }
    }
}

