package com.example.composepractice.data


data class MusicUrlData(
    val data: List<MusicUrl>,
)

data class MusicUrl(
    val url: UrlString,
)