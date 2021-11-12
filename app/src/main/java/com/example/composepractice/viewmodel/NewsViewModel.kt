package com.example.composepractice.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.composepractice.data.NewsModelModel
import com.example.composepractice.data.StoryModel
import com.example.composepractice.http.ApiService

class NewsViewModel :BaseViewModel() {

    val newsLiveData = MutableLiveData<NewsModelModel>()

    fun getNewsList(){
        launch {
            val newModel = ApiService.getNews()
            Log.e("zzf",newModel.toString())
            newModel.stories.add(0, StoryModel())
            newsLiveData.value = newModel
        }
    }
}