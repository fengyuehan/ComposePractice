package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.composewidget.R

/**
 * image 图片
    topLeft 左上角
    alpha 透明度
    style 绘制类型是Fill还是Stoke
    colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。
    blendMode 混合模式后面讲
    srcOffset IntOffset类型原图像的偏移
    srcSize IntSize类型 原图像的大小
    dstOffset IntOffset 类型目标图像的左上角的位置
    dstSize IntSize大小 目标图像的大小
 */
@Composable
fun DrawImage(){
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.launch_logo)
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w= size.width
        val h = size.height

        drawImage(
            image = imageBitmap,
            topLeft = Offset(50f,50f),
            alpha = 1f,
            style = Fill
        )

        drawImage(
            image = imageBitmap,
            srcOffset = IntOffset(0,0),
            srcSize = IntSize(imageBitmap.width,imageBitmap.height),
            dstOffset = IntOffset(50,imageBitmap.height+100),
            dstSize = IntSize(200,200),
            blendMode = BlendMode.SrcOver
        )
    }

}