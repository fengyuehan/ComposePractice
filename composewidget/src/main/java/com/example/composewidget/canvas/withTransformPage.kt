package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform

@Composable
fun withTransformDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height

        withTransform({
            translate(left = canvasWidth/5F)
            rotate(degrees=45F)
        }) {
            drawRect(
                color = Color.Gray,
                topLeft = Offset(x = canvasWidth / 3F, y = canvasHeight / 3F),
                size = canvasSize / 3F
            )
        }
    }
}