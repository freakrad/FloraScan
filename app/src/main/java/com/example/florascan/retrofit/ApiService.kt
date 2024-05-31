package com.example.florascan.retrofit

import com.example.florascan.ui.news.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=id&category=science")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}