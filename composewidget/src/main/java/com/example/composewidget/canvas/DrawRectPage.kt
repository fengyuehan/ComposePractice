package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * brush 刷子
 * color 颜色
    topLeft 左上角点的坐标
    size 大小，比如Size(w,h) w是宽度，h是高度
    cornerRadius 是具体的圆角
    alpha 透明度
    style DrawStyle 可以是Fill填充，也可以是Stroke，线条
    colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。
    blendMode 混合模式
 */
@Composable
fun DrawRectDemo(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w= size.width
        val h = size.height
        // 蓝底的矩形
        drawRect(
            color = Color.Blue,
            topLeft = Offset(100f,100f),
            size = Size(100f,100f),
            alpha = 1f,
            style = Fill
        )

        // 渐变的矩形
        drawRect(
            brush = Brush.linearGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                start = Offset(300f, 100f),
                end = Offset(300f, 200f),
                tileMode = TileMode.Repeated),
            topLeft = Offset(300f,100f),
            size = Size(100f,100f),
            alpha = 1f,
            style = Fill
        )

        // 线框的矩形
        drawRect(
            color = Color.Blue,
            topLeft = Offset(100f,300f),
            size = Size(100f,100f),
            alpha = 1f,
            style = Stroke(width=1f,cap = StrokeCap.Butt)
        )

        // 线框的矩形
        drawRoundRect(
            color = Color.Blue,
            topLeft = Offset(300f,300f),
            size = Size(300f,100f),
            alpha = 1f,
            style = Fill,
            cornerRadius = CornerRadius(10f,10f)
        )

        // 渐变的矩形
        drawRoundRect(
            brush = Brush.linearGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                start = Offset(300f, 450f),
                end = Offset(600f, 550f),
                tileMode = TileMode.Repeated),
            topLeft = Offset(300f,450f),
            size = Size(300f,100f),
            alpha = 1f,
            style = Fill,
            cornerRadius = CornerRadius(10f,10f)
        )
    }
}