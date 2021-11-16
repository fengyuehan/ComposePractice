package com.example.composewidget.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * drawerContent 左边的内容
    modifier 修饰符 Modifier用法详解
    drawerState ModalDrawer的状态 有DrawerValue.open打开，DrawerValue.Closed关闭
    gesturesEnabled 是否支持手势去打开和关闭抽屉
    drawerShape 形状
    drawerElevation 阴影
    drawerBackgroundColor 背景颜色
    drawerContentColor 内容的颜色
    scrimColor 当左边drawer控件显示的时候，右边余留出来的颜色
BottomDrawercontent ModalDrawer的内容

 */
@Composable
fun modalDrawerTest(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalDrawer(
        drawerContent = {
            Text(text = "ModalDrawer Content",modifier = Modifier.fillMaxWidth().height(200.dp))
        },
        //        modifier = Modifier.fillMaxWidth().height(200.dp),
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerShape = MaterialTheme.shapes.large,
        drawerElevation = DrawerDefaults.Elevation,
        drawerBackgroundColor = Color.Red,
        drawerContentColor = Color.White,
        scrimColor = DrawerDefaults.scrimColor
    ) {
        Column() {
            Text(text = "ModalDrawer Title")
            Text(text = "ModalDrawer Content")
            Button(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Text(text = "打开")
            }
        }
    }
}
