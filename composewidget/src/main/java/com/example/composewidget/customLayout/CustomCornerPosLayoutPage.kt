package com.example.composewidget.customLayout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

/**
 * constraints可以理解为父布局的约束
 * Intrinsic Measurement(固有特性测量)。Intrinsic Measurement 是允许父项对子项测量之前，先让子项测量下自己的最大最小的尺寸。IntrinsicSize.Min跟IntrinsicSize.Max
 */
//Modifier.layout修饰符仅更改调用的可组合项
fun Modifier.customCornerPosLayout(pos:CornerPosition) = layout { measurable, constraints ->
    //这里可以理解为测量子控件的约束，测量出来就可以得到子项的宽高这些
    val placeable = measurable.measure(constraints)
    //进行摆放
    layout(constraints.maxWidth,constraints.maxHeight){
        when(pos){
            CornerPosition.TopLeft -> {
                placeable.placeRelative(0, 0)
            }
            CornerPosition.TopRight -> {
                placeable.placeRelative(constraints.maxWidth-placeable.width, 0)
            }
            CornerPosition.BottomLeft->{
                placeable.placeRelative(0, constraints.maxHeight-placeable.height)
            }
            CornerPosition.BottomRight -> {
                placeable.placeRelative(constraints.maxWidth-placeable.width, constraints.maxHeight-placeable.height)
            }
        }
    }
}