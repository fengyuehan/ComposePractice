package com.example.composewidget.canvas




import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint

import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

@Composable
fun drawIntoCanvasDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        drawIntoCanvas {
            val paint = Paint()
            paint.color = Color.Red
            paint.strokeWidth = 10f
            it.drawLine(p1= Offset(50f,50f),p2= Offset(200f,200f),paint = paint)
        }
    }
}