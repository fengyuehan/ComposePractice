package com.example.composewidget.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
 * appBar 顶部的控件，可用我们之前讲过的TopAppBar TopAppBar讲解
    backLayerContent 显示在后面被遮挡的内容
    frontLayerContent 显示在前面的内容
    modifier 修饰符
    scaffoldState 状态。可以设置snackbar的状态。还可以设置BackdropScaffoldState的状态。有BackdropValue.Concealed. 表示隐藏了后一层，而激活了前一层

    跟BackdropValue.Revealed 表示后一层显示，前一层隐藏

    gesturesEnabled 可否可用通过手指拖动的形式显示和隐藏前后界面
    peekHeight 前面的界面举例顶部的距离，也可以理解成后面的界面默认露出显示的高度。默认是56dp
    headerHeight
    persistentAppBar 当为true的时候，不管显示还是隐藏后面的界面，appBar都一直显示的。如果值为false，则显示后面的界面的时候，appBar会隐藏
    stickyFrontLayer 当为true的时候，前面的界面之后下拉到后面界面的内容高度位置。为false 的时候，前面的界面可以一直下拉到屏幕底部（试试效果就知道）
    backLayerBackgroundColor 后面界面的背景颜色
    backLayerContentColor 后面界面的内容的颜色
    frontLayerShape 前面界面的形状
    frontLayerElevation 前面界面的阴影
    frontLayerBackgroundColor 前面界面的背景颜色
    frontLayerContentColor 前面界面的内容的颜色
    frontLayerScrimColor 前面界面下拉时，顶部预留出来的空白的颜色
    snackbarHost 设置Snackbar的
 */
@ExperimentalMaterialApi
@Composable
fun backdropScaffoldTest(){
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val scope = rememberCoroutineScope()
    BackdropScaffold(
        appBar = { topBarView(scaffoldState = scaffoldState) },
        backLayerContent = {
            Column() {
                Text(text = "backLayerContent Title")
                Text(text = "backLayerContent Content")
                Text(text = "backLayerContent Content1")
                Text(text = "backLayerContent Content2")
                Text(text = "backLayerContent Content3")
                Text(text = "backLayerContent Content4")
                Text(text = "backLayerContent Content5")
                Text(text = "backLayerContent Content6")
                Text(text = "backLayerContent Content7")
                Text(text = "backLayerContent Content8")
                Text(text = "backLayerContent Content9")
                Text(text = "backLayerContent Content10")
            }
        },
        frontLayerContent = {
            Column() {
                Text(text = "frontLayerContent Title")
                Text(text = "frontLayerContent Content")
                Text(text = "frontLayerContent Content1")
                Text(text = "frontLayerContent Content2")
                Text(text = "frontLayerContent Content3")
                Text(text = "frontLayerContent Content4")
                Text(text = "frontLayerContent Content5")
                Text(text = "frontLayerContent Content6")
                Text(text = "frontLayerContent Content7")
                Text(text = "frontLayerContent Content8")
                Text(text = "frontLayerContent Content9")
                Text(text = "frontLayerContent Content10")
                Button(onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("显示了Snackbar的内容","隐藏",SnackbarDuration.Short)
                    }}) {
                    Text(text = "打开Snackbar")
                }
            }
        },
        scaffoldState = scaffoldState,
        gesturesEnabled = true,
        peekHeight = 48.dp,
        persistentAppBar = true,
        stickyFrontLayer = true,
        backLayerBackgroundColor = Color.Red,
        backLayerContentColor = Color.White,
        frontLayerShape = BackdropScaffoldDefaults.frontLayerShape,
        frontLayerElevation = BackdropScaffoldDefaults.FrontLayerElevation,
        frontLayerBackgroundColor = Color.White,
        frontLayerContentColor = Color.Black,
        frontLayerScrimColor = BackdropScaffoldDefaults.frontLayerScrimColor,
    )
}


@ExperimentalMaterialApi
@Composable
fun topBarView(scaffoldState:BackdropScaffoldState){
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
                            if (scaffoldState.isConcealed) scaffoldState.reveal() else scaffoldState.conceal()
                        }
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "页面标题",fontSize = 17.sp,color = Color.White)
        }
    }
}

