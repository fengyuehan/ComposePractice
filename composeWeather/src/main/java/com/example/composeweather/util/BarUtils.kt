package com.example.composeweather.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * 设置透明状态栏
 */
fun Activity.transparentStatusBar() {
    transparentStatusBar(window)
}

private fun transparentStatusBar(window: Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    val vis = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = option or vis
    window.statusBarColor = Color.TRANSPARENT
}


/**
 * 状态栏反色
 */
fun Activity.setAndroidNativeLightStatusBar() {
    val decor = window.decorView
    val isDark = resources.configuration.uiMode == 0x21
    if (!isDark) {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        decor.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}


/**
 * 获取当前是否为深色模式
 * 深色模式的值为:0x21
 * 浅色模式的值为:0x11
 * @return true 为是深色模式   false为不是深色模式
 */
fun Context.isDarkMode(): Boolean {
    return resources.configuration.uiMode == 0x21
}