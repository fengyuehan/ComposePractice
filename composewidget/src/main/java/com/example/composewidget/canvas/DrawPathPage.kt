package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

/*path 路径
brush 刷子
color 颜色
alpha 透明度
style 填充类型
colorFilter 用于修改在其安装的[Paint]上绘制的每个像素的颜色的效果。Image讲解里有讲到
blendMode 混合模式*/
@Composable
fun DrawPathDemo(){
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path()
        path.moveTo(50f,50f)
        path.lineTo(50f,150f)
        path.lineTo(200f,50f)
        path.close()
        drawPath(
            path = path,
            color = Color.Red
        )

        path.moveTo(50f,200f)
        path.lineTo(50f,350f)
        path.lineTo(200f,200f)
        path.close()
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(width = 4f)
        )
    }

}