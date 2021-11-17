package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap

/*
points 点的集合
pointMode 用于指示如何绘制点。有三种取值PointMode.Points 分别画点，PointMode.Lines 画线(点集合两两组合划线，奇数的话最后一个不管)，PointMode.Polygon 画多边形
color 颜色
brush 刷子 设置渐变
strokeWidth 宽度
pathEffect 跟上面drawLine的一致
alpha 透明度
colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。
blendMode 混合模式
*/

@Composable
fun drawPointDemo(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val points = ArrayList<Offset>().apply {
            add(Offset(50f,50f))
            add(Offset(100f,50f))
            add(Offset(100f,100f))
            add(Offset(80f,100f))
        }

        val points2 = ArrayList<Offset>().apply {
            add(Offset(50f,250f))
            add(Offset(100f,250f))
            add(Offset(100f,300f))
            add(Offset(80f,300f))
            add(Offset(300f,300f))
        }

        val points3 = ArrayList<Offset>().apply {
            add(Offset(250f,250f))
            add(Offset(300f,250f))
            add(Offset(300f,300f))
            add(Offset(280f,300f))
            add(Offset(250f,250f))
        }

        drawPoints(
            points = points,
            pointMode = PointMode.Points,
            color = Color.Blue,
            strokeWidth = 15f,
            alpha = 1f
        )

        drawPoints(
            points = points2,
            pointMode = PointMode.Lines,
            color = Color.Blue,
            strokeWidth = 5f,
            alpha = 1f
        )

        drawPoints(
            points = points3,
            pointMode = PointMode.Polygon,
            color = Color.Blue,
            strokeWidth = 5f,
            cap = StrokeCap.Round,
            alpha = 1f
        )
    }
}