package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

/*brush 刷子跟上面一致
color 颜色
topLeft 左上角的坐标
size 大小
alpha 透明度
style 填充类型
colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。Image讲解里有讲到
blendMode 混合模式*/

@Composable
fun DrawOvalDemo(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w= size.width
        val h = size.height

        drawOval(
            color = Color.Red,
            topLeft = Offset(50f,50f),
            size = Size(200f,100f),
            alpha = 1f,
            style = Stroke(width = 5f)
        )

        drawOval(
            color = Color.Red,
            topLeft = Offset(300f,50f),
            size = Size(200f,100f),
            alpha = 1f,
            style = Fill
        )

        drawOval(
            brush = Brush.horizontalGradient(
                0.0f to Color.Red,
                0.5f to Color.Green,
                1.0f to Color.Blue,
                startX = 50f,
                endX = 250f,
            ),
            topLeft = Offset(50f,180f),
            size = Size(200f,100f),
            alpha = 1f,
            style = Fill
        )
    }
}