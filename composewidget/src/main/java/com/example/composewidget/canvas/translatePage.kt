package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate

/**
 * left 左边平移多少
    top顶部平移多少
 */
@Composable
fun translateDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasQuadrantSize = size / 2F
        translate(canvasQuadrantSize.width,0f){
            drawRect(
                color = Color.Blue,
                size = canvasQuadrantSize
            )
        }
    }
}