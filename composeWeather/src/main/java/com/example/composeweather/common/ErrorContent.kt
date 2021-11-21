package com.example.composeweather.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.composeweather.R

@Composable
fun ErrorPage(onClick:() -> Unit){
    //定义所需要加载的动画
    //从源码可以看到加载动画的方式有5种
    //1.从raw文件夹中加载 RawRes（）
    //2.加载网络动画  url()
    //3.从文件夹中加载 file()
    //4.从Assest中加载 Asset（）
    //5.加载json字符串 JsonString（）
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.weather_error))
    //设置动画进度
//    isPlaying：表示动画当前是否正在播放。需要注意的是，动画可能会因为达到目标迭代次数而结束，如果发生这种情况，就算将此参数设置为 true 动画也可能会停止；
//    restartOnPlay：如果 isPlaying 从 false 切换到 true，restartOnPlay 用来确定是否重置进度和迭代；
//    clipSpec：用于指定动画播放应该被剪辑到的边界（本文不展开介绍此参数）；
//    speed：动画应该播放的速度，大于 1 的话会加快速度， 0 到 1 之间的话会减慢它的速度，小于 0 的话将向后播放；
//    iterations：动画在停止前应重复的次数（正整数），如果想要永远重复可以设置为 LottieConstants.IterateForever，LottieConstants.IterateForever 其实就是 Integer.MAX_VALUE；
//    cancellationBehavior：动画在取消时的行为，如果有一个基于状态的转换，并希望在继续播放下一个动画之前完成播放，可以设置为 LottieCancellationBehavior.OnIterationFinish。
    val progress by animateLottieCompositionAsState(composition = composition,iterations = LottieConstants.IterateForever)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            color = MaterialTheme.colors.onSecondary
        ),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition,
            progress = progress,
            modifier = Modifier.size(130.dp))
        Button(onClick = onClick){
            Text(text = stringResource(id = R.string.bad_network_view_tip))
        }

    }
}