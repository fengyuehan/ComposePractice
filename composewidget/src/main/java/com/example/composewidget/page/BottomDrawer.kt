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
 * drawerContent 底部抽屉的内容
    modifier 修饰符
    drawerState 抽屉的状态，BottomDrawerValue.Open 打开一半 BottomDrawerValue.Closed 关闭，BottomDrawerValue.Expanded 是完全打开
    gesturesEnabled 是否支持手势去打开和关闭抽屉
    drawerShape 抽屉形状
    drawerElevation 抽屉阴影
    drawerBackgroundColor 抽屉背景颜色
    drawerContentColor 抽屉内容颜色
    scrimColor 抽屉打开时候，顶部剩余空间的颜色
    content BottomDrawer的内容
 */
@ExperimentalMaterialApi
@Composable
fun bottomDrawerTest(){
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Open)
    val scope = rememberCoroutineScope()
    BottomDrawer(
        drawerContent = {
            Text(text = "Bottom Drawer Content",modifier = Modifier
                .fillMaxWidth()
                .height(600.dp))
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
            Text(text = "BottomDrawer Title")
            Text(text = "BottomDrawer Content")
            Button(onClick = {
                scope.launch {
                    drawerState.expand()
                }
            }) {
                Text(text = "打开")
            }
        }
    }
}
