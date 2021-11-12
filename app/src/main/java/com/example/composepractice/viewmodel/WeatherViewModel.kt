package com.example.composepractice.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.composepractice.http.ApiService
import com.example.composepractice.ui.weather.WeatherImageFactory
import com.example.composepractice.ui.weather.WeatherModel

class WeatherViewModel :BaseViewModel() {
    val weatherLiveData = MutableLiveData<List<WeatherModel>>()

    fun getWeathers() {
        launch {
            val weatherResponse = ApiService.getWeathers()
            weatherResponse.result.forEach {
                it.imgRes = WeatherImageFactory.getWeatherImage(it.weather)
            }
            weatherLiveData.value = weatherResponse.result
        }
    }
}