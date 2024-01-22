package com.example.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.model.NewsApiResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class HomeViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val newsApiService: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    init {
        loadTopHeadlines()
    }

    private fun loadTopHeadlines() {
        viewModelScope.launch {
            try {
                val newsApiResponse = newsApiService.getTopHeadlines("us", "technology", "ad47dee143a142caa85d4240597de0aa")
                _articles.value = newsApiResponse.articles
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface NewsApiService {
        @GET("top-headlines")
        suspend fun getTopHeadlines(
            @Query("country") country: String,
            @Query("category") category: String,
            @Query("apiKey") apiKey: String
        ): NewsApiResponse
    }
}