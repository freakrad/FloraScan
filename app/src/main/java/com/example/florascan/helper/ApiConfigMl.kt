package com.example.florascan.helper

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfigMl {
    fun getApiService(): ApiServiceMl {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // 30 seconds
            .readTimeout(30, TimeUnit.SECONDS) // 30 seconds
            .writeTimeout(30, TimeUnit.SECONDS) // 30 seconds
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://florascan-server-37eqej7ehq-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiServiceMl::class.java)
    }
}