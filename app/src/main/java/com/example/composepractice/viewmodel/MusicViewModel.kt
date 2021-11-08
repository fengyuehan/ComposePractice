package com.example.composepractice.viewmodel

import androidx.annotation.DrawableRes
import com.example.composepractice.R

class MusicViewModel :BaseViewModel() {
    fun menuList() = listOf(MainMenu.Fund(),MainMenu.NetEaseMusic())
}

/**
 * Sealed class（密封类）的所有子类都必须与密封类在同一文件中
 * Sealed class（密封类）的子类的子类可以定义在任何地方，并不需要和密封类定义在同一个文件中
 * Sealed class（密封类）没有构造函数，不可以直接实例化，只能实例化内部的子类
 */
sealed class MainMenu(val name: String, @DrawableRes val icon: Int = R.drawable.discuss) {
    class Fund : MainMenu("基金", R.drawable.discuss)
    class NetEaseMusic : MainMenu("音乐", R.drawable.music)
}