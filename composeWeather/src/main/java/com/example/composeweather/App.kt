package com.example.composeweather

import android.app.Application
import com.qweather.sdk.view.HeConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application() {
    companion object{
        // 和风天气 Public Id
        private const val WEATHER_PUBLIC_ID = "HE2111201130191519"
        // 和风天气 Key
        private const val WEATHER_KEY = "422738b795ac46c6b984a258432b9796"
    }

    override fun onCreate() {
        super.onCreate()
        //初始化和风天气
        HeConfig.init(WEATHER_PUBLIC_ID, WEATHER_KEY)
        //切换至开发版服务
        HeConfig.switchToDevService()
    }
}