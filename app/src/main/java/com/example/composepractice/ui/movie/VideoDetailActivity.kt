package com.example.composepractice.ui.movie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import cn.jzvd.Jzvd
import com.example.composepractice.BaseActivity
import com.example.composepractice.R

class VideoDetailActivity:BaseActivity() {
    companion object{
        private const val VIDEO_URL = "video_url"
        private const val VIDEO_TITLE = "video_title"
        fun go(context: Activity, url:String, title:String){
            Intent(context,VideoDetailActivity::class.java).also {
                it.putExtra(VIDEO_URL,url)
                it.putExtra(VIDEO_TITLE,title)
                context.startActivity(it)
                context.overridePendingTransition(
                    R.anim.slide_bottom_in,
                    0
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(VIDEO_URL)!!
        val title = intent.getStringExtra(VIDEO_TITLE)!!
        setContent {
            VideoViewPage(url,title){
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()){
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}