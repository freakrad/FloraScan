package com.example.florascan.retrofit

import com.example.florascan.ui.news.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
<<<<<<< HEAD
//    @GET("top-headlines?country=id&category=science")
    @GET("everything?q=plant-watering-tips")
=======
    @GET("top-headlines?country=id&category=science")
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}