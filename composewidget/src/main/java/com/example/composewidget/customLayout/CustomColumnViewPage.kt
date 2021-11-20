package com.example.composewidget.customLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun CustomColumnView(layoutDirection: LayoutDirection,modifier: Modifier = Modifier,content :@Composable () -> Unit){
    Layout(modifier = modifier,content = content){ measurables, constraints ->
        var totalHeight = 0;
        var maxWidth = 0;
        val placeables  = measurables.map {
            val placeable = it.measure(constraints = constraints)
            totalHeight += placeable.height
            if (placeable.width > maxWidth){
                maxWidth = placeable.width
            }
            placeable
        }
        layout(maxWidth,totalHeight){
            if (layoutDirection == LayoutDirection.TtB){
                var y = 0
                placeables.forEach{
                    it.placeRelative(0,y)
                    totalHeight += it.height
                }
            }else{
                var  y = totalHeight
                placeables.forEach{
                    totalHeight -= it.height
                    it.placeRelative(0,y)
                }
            }
        }

    }
}