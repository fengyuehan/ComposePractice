package com.example.composewidget.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * backgroundColor 背景颜色
    contentColor 内容的颜色
    elevation 阴影
    contentPadding 内容边距 可通过PaddingValue设置，也可使用默认AppBarDefaults.ContentPadding
    content 内容控件
 */
@Composable
fun topBarView(){
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
                contentDescription = "menu按钮",
                tint = Color.White,
                modifier = Modifier
                    .clickable {
                        //点击了按钮
                    }
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxHeight()
            )
            Text(text = "页面标题",fontSize = 17.sp,color = Color.White)
        }
    }
}
