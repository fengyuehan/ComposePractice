package com.example.composepractice

import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    override fun finish() {
        super.finish()
        overridePendingTransition(
            0,
            R.anim.slide_bottom_out
        )
    }
}