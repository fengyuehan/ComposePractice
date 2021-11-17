package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate

/**
 * degrees 顺时针旋转的角度
pivot 旋转的中心点 （默认是图形的中心）
radians 顺时针旋转的弧度
pivot 旋转的中心点（默认是图形的中心）
 */
@Composable
fun rotatePage(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height
        rotate(degrees = 45F){
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F , y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }


}