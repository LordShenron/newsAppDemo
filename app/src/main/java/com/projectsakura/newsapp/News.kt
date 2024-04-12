package com.projectsakura.newsapp

data class News(
    val title: String,
    val url: String,
    val description: String,
    val urlToImage: String = "",
    var content: String? = null,
    var isContentFetched: Boolean = false
)