package com.example.composewidget.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * sheetContent 底部抽屉的内容
    modifier 修饰符
    sheetState 设置状态。显示和隐藏底部抽屉 默认实现是rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)。ModalBottomSheetValue.Hidden是隐藏，ModalBottomSheetValue.Expanded打开，ModalBottomSheetValue.HalfExpanded 打开一半
    sheetShape 设置底部抽屉形状
    sheetElevation 设置底部抽屉的阴影
    sheetBackgroundColor 设置底部抽屉的背景颜色
    sheetContentColor 设置底部抽屉内容的颜色
    scrimColor 设置底部抽屉打开的时候，顶部剩余部分的颜色
    content ModalBottomSheetLayout的内容
 */
@ExperimentalMaterialApi
@Composable
fun  modalBottomSheetLayoutTest(){
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetContent = {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = " sheetContent title")
            Text(text = " sheetContent content")
            Text(text = " sheetContent content2")
            Text(text = " sheetContent content3")
            Text(text = " sheetContent content4")
            Text(text = " sheetContent content5")
            Text(text = " sheetContent content6")
            Text(text = " sheetContent content7")
            Text(text = " sheetContent content8")
            Text(text = " sheetContent content9")
            Spacer(modifier = Modifier.height(10.dp))
        },
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(4.dp),
        sheetElevation = 16.dp,
        sheetBackgroundColor = Color.Red,
        sheetContentColor = Color.White,
        scrimColor = ModalBottomSheetDefaults.scrimColor)
    {
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = " ModalBottomSheetLayout title")
            Text(text = " ModalBottomSheetLayout content")
            Button(onClick = {
                scope.launch {
                    if(sheetState.isVisible) sheetState.hide() else sheetState.show()
                }
            }) {
                Text(text = " 打开")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}