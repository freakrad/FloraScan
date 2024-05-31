package com.example.florascan.ui.news

import androidx.lifecycle.ViewModel

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
}