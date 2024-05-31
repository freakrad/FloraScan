package com.example.florascan.di

import android.content.Context
import com.example.florascan.retrofit.ApiConfig
import com.example.florascan.room.NewsDatabase
import com.example.florascan.ui.news.NewsRepository
import com.example.florascan.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}