package com.example.composewidget.customLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun customCornerPosLayoutTest(){
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .size(100.dp)
            .background(color = Color.Red)) {
            Box(modifier = Modifier
                .customCornerPosLayout(CornerPosition.BottomRight)
                .size(10.dp)
                .background(color = Color.Blue, shape = CircleShape))
        }
        CustomColumnView(layoutDirection = LayoutDirection.TtB,modifier = Modifier.background(Color.Red)) {
            Text(text = "第一个Text第一个Text第一个Text第一个Text")
            Text(text = "第二个Text")
            Text(text = "第三个Text")
            Text(text = "第四个Text")
            Text(text = "第五个Text第五个Text")

        }
        CustomColumnView(layoutDirection = LayoutDirection.BtT,modifier = Modifier.background(Color.Red)) {
            Text(text = "第一个Text第一个Text第一个Text第一个Text")
            Text(text = "第二个Text")
            Text(text = "第三个Text")
            Text(text = "第四个Text")
            Text(text = "第五个Text第五个Text")

        }
    }

}
