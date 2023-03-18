package com.dicoding.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.newsapp.BuildConfig
import com.dicoding.newsapp.data.local.entity.NewsEntity
import com.dicoding.newsapp.data.local.room.NewsDao
import com.dicoding.newsapp.data.remote.retrofit.ApiService
import com.dicoding.newsapp.utils.AppExecutors
import kotlinx.coroutines.Dispatchers

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {

    /**
     * Sama seperti MediatorLiveData, liveData block dapat digunakan
     * untuk membuat LiveData baru maupun menggabungkan LiveData
     * yang sudah ada. Apabila data sumber bukan merupakan LiveData,
     * gunakan fungsi emit. Sedangkan apabila data sumber berupa LiveData, gunakan fungsi emitSource.
     * Salah satu kelebihan liveData block yaitu ia
     * mendukung Coroutine sehingga bisa menjalankan
     * suspend function di dalamnya. Untuk memahami lebih
     * lanjut, Anda dapat membaca beberapa tautan berikut:
     * @see <a href="https://developer.android.com/topic/libraries/architecture/coroutines?hl=id">liveData</a>
     * */
    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { articles ->
                val isBookmarked = newsDao.isNewsBookmarked(articles.title)
                NewsEntity(
                    articles.title,
                    articles.publishedAt,
                    articles.urlToImage,
                    articles.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        // Consumer
        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map {
            // Producer
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getBookMarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    suspend fun setBookMarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}