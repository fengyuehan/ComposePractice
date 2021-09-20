package com.example.composepractice.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.composepractice.mApp

object SpUtils {

    private const val SP_FILE_NAME = "TEST"

    private val prefs :SharedPreferences by lazy {
        mApp.getSharedPreferences(SP_FILE_NAME,Context.MODE_PRIVATE)
    }

    fun getBool(name:String) :Boolean = prefs.getBoolean(name,false)

    fun setBoolean(name: String,value: Boolean){
        with(prefs.edit()){
            putBoolean(name,value)
            apply()
        }

        //等价于   同一个对象调用其不同的方法
        /*var edit:SharedPreferences.Editor = prefs.edit()
        edit.putBoolean(name,value)
        edit.apply()*/
    }
}