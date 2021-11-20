package com.example.composeweather.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.qweather.sdk.bean.base.Lang


/**
 * 获取默认语言
 */
@RequiresApi(Build.VERSION_CODES.N)
fun getDefaultLocale(context: Context?): Lang {
    if (context == null) return Lang.ZH_HANS
    XLog.d("getDefaultLocale: ${context.resources.configuration.locales[0].toLanguageTag()}")
    return when (context.resources.configuration.locales[0].toLanguageTag()) {
        "zh", "zh-CN" -> Lang.ZH_HANS
        "zh_rHK", "zh_rTW", "zh_HK", "zh_TW", "HK", "TW", "zh-TW", "zh-HK" -> Lang.ZH_HANT
        "es", "en" -> Lang.EN
        else -> Lang.EN
    }
}
