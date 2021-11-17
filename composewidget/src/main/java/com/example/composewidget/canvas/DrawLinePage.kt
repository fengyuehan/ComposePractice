package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*


/**
 * brush 是刷子，就填充线的颜色的刷子，主要是处理渐变的 主要有linearGradient，horizontalGradient，verticalGradient，radialGradient，sweepGradient
 * color 是线的颜色
 * start 是线的起始点位置
 * end 是线的终点位置
 * strokeWidth 线的宽度
 * cap 线段末端的形状。 三种取值：StrokeCap.Butt平的效果（以平边开始和结束轮廓，没有延伸。）StrokeCap.Round 圆形效果（以半圆延伸开始和结束轮廓）StrokeCap.Square 平的效果（以半正方形延伸开始和结束轮廓）
 * pathEffect 设置显示效果（比如虚线）
 *    PathEffect.cornerPathEffect(radius: Float) 将线段之间的锐角替换为指定半径的圆角 radius是半径
 *    PathEffect.dashPathEffect(intervals: FloatArray, phase: Float = 0f) 将形状绘制为具有给定间隔的一系列破折号。比如虚线  例如interval={20，5}，第一个参数表示虚线的长度是20，5是虚线之间的间隔是5.  phase 偏移
 *    PathEffect.chainPathEffect(outer: PathEffect, inner: PathEffect) 创建一个PathEffect，将内部效果应用于路径，然后应用外部效果
 *    PathEffect.stampedPathEffect(shape: Path, advance: Float, phase: Float,style: StampedPathEffectStyle) 用path表示的指定形状冲压绘制的路径.  shape要踩踏的路径,advance 每个冲压形状之间的前进间距, phase 在压印第一个形状之前要偏移的相位量, style如何在每个位置转换形状，因为它是冲压. style有三种取值 StampedPathEffectStyle.Translate 平移 ，StampedPathEffectStyle.Rotate 旋转，StampedPathEffectStyle.Morph 变形
 * alpha 透明度
 * colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果
 * blendMode 混合模式
 */
@Composable
fun DrawLineDemo(){
    Canvas(modifier = Modifier.fillMaxSize(),onDraw = {
        val w = size.width
        val h = size.height
        // 蓝色的线
        drawLine(
            start = Offset(100f,100f),
            end = Offset(w-100f,100f),
            color = Color.Blue,
            strokeWidth = 10f,
            cap = StrokeCap.Square,
            alpha = 1f
        )

        // 渐变的线
        drawLine(
            brush = Brush.linearGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                start = Offset(100f, 150f),
                end = Offset(w-100f, 150f),
                tileMode = TileMode.Repeated),
            start = Offset(100f,150f),
            end = Offset(w-100f,150f),
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            alpha = 1f
        )

        // 横向渐变的线
        drawLine(
            brush = Brush.horizontalGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                startX = 100f,
                endX = w-100f,
                tileMode = TileMode.Mirror),
            start = Offset(100f,200f),
            end = Offset(w-100f,200f),
            strokeWidth = 10f,
            cap = StrokeCap.Butt,
            alpha = 1f
        )

        // 竖直渐变的线
        drawLine(
            brush = Brush.verticalGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                startY = 250f,
                endY = 350f,
                tileMode = TileMode.Clamp),
            start = Offset(100f,250f),
            end = Offset(100f,350f),
            strokeWidth = 10f,
            cap = StrokeCap.Round,
            alpha = 1f
        )

        // 横向渐变的有 pathEffect.dashPathEffect的虚线
        drawLine(
            brush = Brush.horizontalGradient(
                0.0f to Color.Red,
                0.5f to Color.Yellow,
                1.0f to Color.Blue,
                startX = 100f,
                endX = w-100f,
                tileMode = TileMode.Clamp),
            start = Offset(100f,450f),
            end = Offset(w-100f,450f),
            strokeWidth = 10f,
            cap = StrokeCap.Butt,
            alpha = 1f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(100f,20f),10f)
        )
    })
}