package com.example.composeweather.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.composeweather.R

@Composable
fun EmptyPage(tips:String = "当前无内容"){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.weather_no_data))
    val progress by animateLottieCompositionAsState(composition = composition,iterations = LottieConstants.IterateForever)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, progress = progress,modifier = Modifier.size(130.dp))
        Text(text = tips,modifier = Modifier.padding(10.dp))
    }
}
