package com.example.composeweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.example.composeweather.ui.theme.ComposePracticeTheme
import com.example.composeweather.util.setAndroidNativeLightStatusBar
import com.example.composeweather.util.transparentStatusBar
import com.example.composeweather.view.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContent {
            ComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavGraph()
                }
            }
        }
    }

    private fun initView() {
        // 加载动画
        installSplashScreen()
        // 状态栏透明
        transparentStatusBar()
        // 状态栏反色
        setAndroidNativeLightStatusBar()
    }


}
