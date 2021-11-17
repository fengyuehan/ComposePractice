package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

/*
brush 刷子
color 颜色
radius 半径
center 圆形
alpha 透明度
style 填充方式 Fill跟Stroke
colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。
blendMode 混合模式
*/

@Composable
fun DrawCircle(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w= size.width
        val h = size.height

        drawCircle(
            color = Color.Red,
            radius = 100f,
            center = Offset(150f,150f),
            alpha = 1f,
            style = Fill
        )

        drawCircle(
            color = Color.Red,
            radius = 100f,
            center = Offset(400f,150f),
            alpha = 1f,
            style = Stroke(width = 5f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                0.0f to Color.Red,
                0.5f to Color.Green,
                1.0f to Color.Blue,
                center = Offset(150f,360f),
                radius = 100f,
                tileMode = TileMode.Clamp
            ),
            radius = 100f,
            center = Offset(150f,360f),
            alpha = 1f,
            style = Fill
        )
    }
}