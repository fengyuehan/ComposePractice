package com.example.composepractice

import android.app.Application
import android.content.Context

lateinit var  mApp:Context

class App :Application(){
    override fun onCreate() {
        super.onCreate()
        mApp = this

    }
}