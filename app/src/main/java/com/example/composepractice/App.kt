package com.example.composepractice

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

lateinit var  mApp:Context

@HiltAndroidApp
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        mApp = this
    }
}