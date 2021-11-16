package com.example.composewidget.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * sheetContent 底部抽屉的内容
 * drawerState可以去控制左边抽屉的状态
 * bottomSheetState 可以去控制底部抽屉的状态
 * snackbarHostState 可以控制Snackbar的状态
 * topBar 顶部控件
 * floatingActionButton 浮动的button
 * floatingActionButtonPosition 设置floatingActionButton的按钮的位置。是在底部居中还是再右下角，有一半会盖在sheetContent上面
 * sheetGesturesEnabled 是否能通过手指滑动去打开和关闭底部的抽屉
 * sheetShape 底部抽屉的形状
 * sheetElevation 底部抽屉的阴影
 * sheetBackgroundColor 底部抽屉的背景颜色
 * sheetContentColor 底部抽屉的内容颜色
 * sheetPeekHeight 是底部抽屉的初始的高度
 * drawerContent 左边抽屉的内容
 * drawerGesturesEnabled 是否能通过手指拖动的形式去打开和关闭左边的抽屉
 * drawerShape 左边抽屉的形状
 * drawerElevation 左边抽屉的阴影
 * drawerBackgroundColor 左边抽屉的背景颜色
 * drawerContentColor 左边抽屉的内容的颜色
 * drawerScrimColor 左边抽屉打开的时候，右边剩余部分的颜色值
 * backgroundColor BottomSheetScaffold控件的背景颜色
 * contentColor BottomSheetScaffold控件的内容的颜色
 * content BottomSheetScaffold控件包含的内容
 */
@ExperimentalMaterialApi
@Composable
fun bottomSheetScaffoldTest(){
    val  scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
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
        scaffoldState = scaffoldState,
        topBar = { topBarView(scaffoldState) },
//        snackbarHost =
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        if(scaffoldState.bottomSheetState.isCollapsed){
                            scaffoldState.bottomSheetState.expand()
                        }else{
                            scaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            ){

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        sheetGesturesEnabled = true,
        sheetShape = RoundedCornerShape(2.dp),
        sheetElevation = 8.dp,
        sheetBackgroundColor = Color.Red,
        sheetContentColor = Color.White,
        sheetPeekHeight = 50.dp,
        drawerContent = {
            drawView()
        },
        drawerGesturesEnabled = true,
        drawerShape = RoundedCornerShape(4.dp),
        drawerElevation = 16.dp,
        drawerBackgroundColor = Color.White,
        drawerContentColor = Color.Black,
        drawerScrimColor = DrawerDefaults.scrimColor,
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "  BottomSheetScaffold  title")
        Text(text = "  BottomSheetScaffold  content")
        Button(onClick = {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar("被打开了","隐藏",SnackbarDuration.Short)
            }
        }) {
            Text(text = "打开SnackBar")
        }
    }

}

@Composable
fun drawView(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalDrawer(
        drawerContent = {
            Text(
                text = "ModalDrawer Content", modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        },
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

@ExperimentalMaterialApi
@Composable
fun topBarView(scaffoldState:BottomSheetScaffoldState){
    val scope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        backgroundColor = MaterialTheme.colors.primarySurface,
        elevation = 4.dp,
        contentPadding = AppBarDefaults.ContentPadding
    ) {
        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "返回按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        // 打开drawer
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "页面标题",fontSize = 17.sp,color = Color.White)
        }
    }
}

