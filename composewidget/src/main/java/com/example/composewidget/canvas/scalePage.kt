package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale

/*scale 缩放的大小
pivot 缩放的中心点
scaleX X轴缩放多少
scaleY Y轴缩放所少
pivot 缩放的中心点*/
@Composable
fun scaleDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height

        scale(scale =0.5f){
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F , y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }
}