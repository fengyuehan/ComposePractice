package com.example.composewidget.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath

/*path 是路径
clipOp 裁剪的类型ClipOp ClipOp.Intersect是裁剪出来的矩形。ClipOp.Difference从当前剪辑中减去*/
@Composable
fun clipPathDemo(){
    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasSize = size
        val canvasWidth = size.width
        val canvasHeight = size.height
        val path = Path()
        path.moveTo(100f,100f)
        path.lineTo(canvasWidth-100f,100f)
        path.lineTo(canvasWidth-100f,canvasHeight-100f)
        path.lineTo(100f,canvasHeight-100f)
        path.close()
        clipPath(
            path = path,
            clipOp = ClipOp.Intersect
        ){
            drawRect(
                color = Color.Gray,
                size = canvasSize
            )
        }
    }
}