package com.example.composepractice.utils

import java.text.DecimalFormat

internal fun Long.simpleNumText():String {
    return when(this){
        in 0..1_0000 -> this.toString()
        in 1_0000..1_0000_0000 -> "${DecimalFormat("0.##").format(this.toDouble() / 1_0000f)}万"
        else -> "${DecimalFormat("0.##").format(this.toFloat() / 1_0000_0000f)}亿"
    }
}