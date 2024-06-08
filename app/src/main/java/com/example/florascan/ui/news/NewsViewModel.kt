package com.example.florascan.ui.news

import androidx.lifecycle.ViewModel
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
}