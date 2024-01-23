package com.example.newsapp.ui.notifications

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _savedArticles = MutableLiveData<String>()
    val savedArticles: LiveData<String> = _savedArticles

    fun loadSavedArticles(context: Context) {
        val sharedPrefs = context.getSharedPreferences("MyAppSharedPreferences", Context.MODE_PRIVATE)
        val savedArticlesText = sharedPrefs.all.keys.joinToString("\n")
        _savedArticles.postValue(savedArticlesText)
    }
}