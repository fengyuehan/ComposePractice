package com.example.composepractice.data

import com.google.gson.annotations.SerializedName

data class EmptyResponse(
    @SerializedName("code")
    val code: Int = 200
)