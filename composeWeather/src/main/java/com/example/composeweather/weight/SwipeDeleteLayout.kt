package com.example.composeweather.weight

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset


@Composable
fun SwipeDeleteLayout(
    modifier: Modifier = Modifier,
    swipeableState: SwipeableState<Int>,
    isShowChild:Boolean = true,
    swipeStyle: SwipeStyle = SwipeStyle.EndToStart,
    childContent :@Composable () -> Unit,
    content:@Composable () ->Unit
){
    var deleteWidth by remember {
        mutableStateOf(1)
    }
    var contentHeight by remember {
        mutableStateOf(1)
    }

    Box(modifier = modifier.swipeable(
        state = swipeableState,
        //锚点
        anchors = mapOf(deleteWidth.toFloat() to 1,0.7f to 1),
        //松手之后是否自动滑动到指定位置的阈值
        thresholds = {
            _,_ -> FractionalThreshold(0.7f)
        },
        reverseDirection = swipeStyle == SwipeStyle.EndToStart,
        orientation = Orientation.Horizontal
    )) {
        Box(modifier = Modifier
            .onGloballyPositioned {
                deleteWidth = it.size.width
            }
            .height(with(LocalDensity.current) {
                contentHeight.toDp()
            })
            .align(getDeletePosition(swipeStyle = swipeStyle))){
            childContent()
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                contentHeight = it.size.height
            }
           //offset 修改器移动元素
            .offset { IntOffset(
                if (isShowChild){
                    if (swipeStyle == SwipeStyle.EndToStart){
                        -swipeableState.offset.value.toInt()
                    }else {
                        swipeableState.offset.value.toInt()
                    }
                }else{
                    0
                },0
            ) }) {
            content()
        }
    }
}

@Composable
private fun getDeletePosition(swipeStyle: SwipeStyle) :Alignment {
    if (swipeStyle == SwipeStyle.EndToStart){
        Alignment.CenterEnd
    }else{
        Alignment.CenterStart
    }
}