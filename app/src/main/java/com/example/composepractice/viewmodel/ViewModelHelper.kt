package com.example.composepractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal fun ViewModel.busyWork(
    state:MutableStateFlow<ViewState>,
    work:suspend CoroutineScope.() -> Any
) = viewModelScope.launch{
    state.emit(ViewState.Busy())
    try{
        when(val result = work()){
            is ViewState -> state.tryEmit(result)
            else -> state.tryEmit(ViewState.Normal)
        }
    }catch (t:Throwable){
        state.tryEmit(ViewState.Error(t.message.orEmpty()))
        t.printStackTrace()
    }
}