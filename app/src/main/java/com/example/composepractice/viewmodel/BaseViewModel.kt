package com.example.composepractice.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.ext.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

open class BaseViewModel :ViewModel(){

    val stateLiveData = MutableLiveData<State>().apply {
        value = State.Loading
    }

    fun launch(block :suspend CoroutineScope.() -> Unit){
        viewModelScope.launch {
            kotlin.runCatching {
                block()
            }.onSuccess {
                stateLiveData.value = State.Success
            }.onFailure {
                stateLiveData.value = State.Error(it.message)
                it.message?.let { it1 -> Log.e("zzf", it1) }
            }
        }
    }
}