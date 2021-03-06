package com.example.composepractice.http

import com.example.composepractice.data.*
import com.example.composepractice.ui.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {

    companion object {
        //API均来自网上开放Api接口，仅供交流学习，切勿商用
        const val NEWS_URL = "4/news/latest"
        const val MOVIE_URL =
            "http://baobab.kaiyanapp.com/api/v4/rankList/videos"
        const val PIC_URL = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/21"
        const val WEATHER_URL =
            "http://api.k780.com/?app=weather.future&weaId=169&&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json"
    }

    @GET(NEWS_URL)
    suspend fun getNews(): NewsModelModel

    @GET
    suspend fun getMovies(@Url url: String = MOVIE_URL): MovieModel

    @GET
    suspend fun getPics(@Url url: String = PIC_URL): PageModel<List<PictureModel>>

    @GET
    suspend fun getWeathers(@Url url: String = WEATHER_URL): WeatherResponse
}