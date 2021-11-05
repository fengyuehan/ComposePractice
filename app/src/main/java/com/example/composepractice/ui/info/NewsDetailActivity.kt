package com.example.composepractice.ui.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.view.CustomWebView
import com.example.composepractice.view.TitleBar


@SuppressLint("RestrictedApi")
class NewsDetailActivity : ComponentActivity() {
    private lateinit var mWebView: WebView;

    companion object{
        const val TITLE = "title"
        const val CONTENT = "content"

        fun go(context: Context,title: String,content:String){
            Intent(context,NewsDetailActivity::class.java).also {
                it.putExtra(TITLE,title)
                it.putExtra(CONTENT,content)
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra(TITLE)!!
        val content = intent.getStringExtra(CONTENT)!!
        setContent{
            ComposePracticeTheme {
                NewsDetailPage(title = title, content = content) {
                    finish()
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun NewsDetailPage(title: String,content: String,backClick:() -> Unit){
        //必须在Composable修饰的方法中使用
        var mProgress by remember { mutableStateOf(-1)}
        Column(modifier = Modifier.fillMaxSize()) {
            TitleBar(text = title,showBack = true,backClick = backClick)
            Box(modifier = Modifier.weight(1.0f).fillMaxWidth()){
                CustomWebView(
                    url = content,
                    onBack = {
                            webView ->
                        if (webView?.canGoBack() == true){
                            webView.goBack()
                        }else{
                            finish()
                        }
                    },
                    initSettings = {
                            webSettings ->
                        webSettings?.apply {
                            javaScriptEnabled = true
                            useWideViewPort = true
                            loadWithOverviewMode = true
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = true
                            javaScriptCanOpenWindowsAutomatically = true
                            cacheMode = WebSettings.LOAD_NO_CACHE
                        }
                    },
                    onProgressChange = {
                            progress ->
                            mProgress = progress
                    },
                    onReceivedError = {
                        error ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //Log.d("AAAA", ">>>>>>${it?.description}")
                        }
                    }
                )
                LinearProgressIndicator(
                    progress = mProgress * 1.0f / 100,
                    modifier = Modifier.
                        fillMaxWidth().
                        height(if(mProgress == 100) 0.dp else 5.dp),
                    color = Color.Red
                )
            }
        }
    }
}





