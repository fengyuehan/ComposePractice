package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect

/*left 左边的坐标
top 顶部的坐标
right 右边的坐标
bottom 底部的坐标
clipOp 裁剪的类型ClipOp ClipOp.Intersect是裁剪出来的矩形。ClipOp.Difference从当前剪辑中减去提供的矩形*/
@Composable
fun clipRectDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height

        clipRect(
            left = 100f,
            top = 100f,
            right = canvasWidth-100f,
            bottom = canvasHeight-100f,
            clipOp = ClipOp.Intersect
        ){
            drawRect(
                color = Color.Gray,
                size = canvasSize
            )
        }
    }
}