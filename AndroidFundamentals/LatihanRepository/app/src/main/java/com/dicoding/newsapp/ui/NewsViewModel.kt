package com.dicoding.newsapp.ui

import androidx.lifecycle.ViewModel
import com.dicoding.newsapp.data.NewsRepository
import com.dicoding.newsapp.data.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val headLineNews get() = newsRepository.getHeadlineNews()
    val bookmarkedNews get() = newsRepository.getBookMarkedNews()

    fun saveNews(news: NewsEntity) {
        newsRepository.setBookMarkedNews(news, true)
    }

    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookMarkedNews(news, false)
    }
}