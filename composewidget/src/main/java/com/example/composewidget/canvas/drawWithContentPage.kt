package com.example.composewidget.canvas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.composewidget.R

@Composable
fun drawWithContentDemo(){
    Column() {
        Image(bitmap = ImageBitmap.imageResource(id = R.drawable.launch_logo),
            contentScale = ContentScale.Crop,
            contentDescription = "头像",
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .redPoint()
                .clip(RoundedCornerShape(4.dp))
        )
    }

}

fun Modifier.redPoint():Modifier=drawWithContent {
    drawContent()
    drawIntoCanvas {
        val paint = Paint().apply {
            color = Color.Red
        }
        it.drawCircle(Offset(size.width-1.dp.toPx(),1.dp.toPx()),5.dp.toPx(),paint = paint)
    }
}
