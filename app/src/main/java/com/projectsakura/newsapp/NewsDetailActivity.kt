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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_news_detail)

        val content = intent.getStringExtra("newsContent")
        val title = intent.getStringExtra("newsTitle")
        newsUrl = intent.getStringExtra("newsUrl")

        val contentTextView = findViewById<TextView>(R.id.news_content_text_view)

        if (content != null) {
            contentTextView.text = content
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

