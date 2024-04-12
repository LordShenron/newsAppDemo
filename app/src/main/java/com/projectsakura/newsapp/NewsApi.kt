package com.projectsakura.newsapp

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun searchNews(
        @Query("country") country: String,
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
