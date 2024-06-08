package com.example.florascan.ui.news

<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.florascan.BuildConfig
import com.example.florascan.retrofit.ApiService
import com.example.florascan.room.NewsDao
import com.example.florascan.ui.news.entity.NewsEntity
import com.example.florascan.ui.news.response.NewsResponse
import com.example.florascan.utils.AppExecutors
import com.example.florascan.utils.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> {
        result.value = Result.Loading
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            val isBookmarked = newsDao.isNewsBookmarked(article.title)
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.url,
                                isBookmarked
                            )
                            newsList.add(news)
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = newsDao.getNews()
        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

<<<<<<< HEAD
    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }
    fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }

=======
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
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