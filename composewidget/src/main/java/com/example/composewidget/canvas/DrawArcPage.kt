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

/*brush 刷子
color 颜色
startAngle 开始的角度
sweepAngle 划过的角度
useCenter 是否连接圆心
topLeft 左上角的位置
size 大小
alpha 透明度
style 填充模式
colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。Image讲解里有讲到
blendMode 混合模式*/

@Composable
fun DrawArcDemo(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w= size.width
        val h = size.height

        drawArc(
            color = Color.Red,
            startAngle = 90f,
            sweepAngle = 100f,
            useCenter = false,
            alpha = 1f,
            style = Fill,
            topLeft = Offset(50f,50f),
            size = Size(200f,200f)
        )

        drawArc(
            color = Color.Red,
            startAngle = 0f,
            sweepAngle = 100f,
            useCenter = true,
            alpha = 1f,
            style = Fill,
            topLeft = Offset(150f,50f),
            size = Size(200f,200f)
        )

        drawArc(
            color = Color.Red,
            startAngle = 0f,
            sweepAngle = 100f,
            useCenter = false,
            alpha = 1f,
            style = Stroke(width = 5f),
            topLeft = Offset(300f,50f),
            size = Size(200f,200f)
        )

        drawArc(
            color = Color.Red,
            startAngle = 0f,
            sweepAngle = 100f,
            useCenter = true,
            alpha = 1f,
            style = Stroke(width = 5f),
            topLeft = Offset(500f,50f),
            size = Size(200f,200f)
        )

        drawArc(
            brush = Brush.horizontalGradient(
                0.0f to Color.Red,
                0.5f to Color.Green,
                1.0f to Color.Blue,
                startX = 50f,
                endX = 250f,
            ),
            startAngle = 0f,
            sweepAngle = 100f,
            useCenter = true,
            alpha = 1f,
            style = Stroke(width = 5f),
            topLeft = Offset(50f,350f),
            size = Size(200f,200f)
        )

        drawArc(
            brush = Brush.horizontalGradient(
                0.0f to Color.Red,
                0.5f to Color.Green,
                1.0f to Color.Blue,
                startX = 200f,
                endX = 450f,
            ),
            startAngle = 0f,
            sweepAngle = 100f,
            useCenter = true,
            alpha = 1f,
            style = Fill,
            topLeft = Offset(200f,350f),
            size = Size(200f,200f)
        )
    }
}