package com.example.composepractice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicSplashActivity : AppCompatActivity() {

    companion object{
        fun go(context: Context){
            context.startActivity(Intent(context,MusicSplashActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_ease_music_splash)
    }

}