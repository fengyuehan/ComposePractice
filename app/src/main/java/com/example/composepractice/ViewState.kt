package com.example.composepractice

abstract class ViewState {
    object Normal : ViewState()

    class Busy(val message: String? = null) : ViewState()

    class Error(val reason: String) : ViewState()
}