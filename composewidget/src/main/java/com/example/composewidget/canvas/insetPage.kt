package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset

/*left左边平移多少
top 顶部平移多少
right 右边平移多少
bottom 底部平移多少
horizontal 横向平移多少
vertical 竖向平移多少*/
@Composable
fun insetDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasQuadrantSize = size / 2F
        drawRect(
            color = Color.Green,
            size = canvasQuadrantSize
        )

        inset(canvasQuadrantSize.width,canvasQuadrantSize.height){
            drawRect(
                color = Color.Red,
                size = canvasQuadrantSize
            )
        }
    }

}