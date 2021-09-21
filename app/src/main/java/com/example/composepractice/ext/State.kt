package com.example.composepractice.ext

import java.lang.Error

sealed class State {
    object Loading :State()
    object Success :State()
    class Error(val errorMsg:String?) :State()

    fun isLoading() = this is Loading

    fun isError() = this is Error
}