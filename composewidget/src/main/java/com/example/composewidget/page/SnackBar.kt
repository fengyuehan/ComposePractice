package com.example.composewidget.page

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * modifier 修饰符
    action 行为的内容是什么控件
    actionOnNewLine 表示action内容是否在新的一行，为true为另为一行，否则会和content的内容叠在一起
    shape形状 默认是MaterialTheme.shapes.small 也就是RoundedCornerShape(4.dp)
    backgroundColor 设置背景颜色
    contentColor 设置内容的颜色
    elevation设置阴影
    content 内容是什么控件

 */
@Composable
fun snackbarTest(){
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        action = {
            TextButton(
                onClick = {
                    Log.e("ccm","点击了隐藏按钮")
                }) {
                Text(text = "隐藏",color = Color.White)
            }
        },
        actionOnNewLine = true,
        shape = MaterialTheme.shapes.small,
        backgroundColor = SnackbarDefaults.backgroundColor,
        contentColor =MaterialTheme.colors.surface,
        elevation = 6.dp
    ) {
        Text(text = "提示的信息",color=Color.White,fontSize = 12.sp)
    }
}

