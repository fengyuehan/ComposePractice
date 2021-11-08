package com.example.composepractice

import android.content.Context
import android.content.Intent

import android.os.Bundle

class FundActivity :BaseActivity() {

    companion object{
        fun go(context: Context){
            context.startActivity(Intent(context,FundActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}