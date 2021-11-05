package com.example.composepractice.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.composepractice.data.MovieItemModel
import com.example.composepractice.http.ApiService

class MovieViewModel : BaseViewModel() {
    val moviesLiveData = MutableLiveData<List<MovieItemModel>>()

    fun getMovieLists() {

        launch {
            val movieModel = ApiService.getMovies()
            moviesLiveData.value = movieModel.itemList
        }
    }
}