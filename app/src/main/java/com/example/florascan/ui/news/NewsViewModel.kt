package com.example.florascan.ui.news

import androidx.lifecycle.ViewModel
<<<<<<< HEAD
import com.example.florascan.ui.news.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()
    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, true)
    }
    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, false)
    }
=======

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
}