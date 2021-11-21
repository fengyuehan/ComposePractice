package com.example.composeweather.common

sealed class ContentState<out R>{
    fun isLoading() = this is Loading
    fun isSuccessful() = this is Success

    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data = $data]"
            is Empty -> "Success[reason = $reason]"
            is Error -> "Error[exception = $error]"
            Loading -> "Loading"
        }
    }
}

data class Success<out T>(val data:T):ContentState<T>()
data class Empty(val reason:String) :ContentState<Nothing>()
data class Error(val error: Throwable) :ContentState<Nothing>()
object Loading:ContentState<Nothing>()