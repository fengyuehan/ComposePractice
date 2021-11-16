package com.example.composewidget.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * icon 显示在左边的控件
    overlineText 显示在第一个小字体的控件
    text 显示在第二个的文本控件
    secondaryText 显示在第三个的文本控件
    singleLineSecondaryText 设置secondaryText是否为singleLine
    trailing 显示在右边的控件
 */
@ExperimentalMaterialApi
@Composable
fun listItemTest(){
    ListItem(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
        icon = {
            Icon(imageVector = Icons.Filled.Home, contentDescription = "home")
        },
        text = {
            Text(text = "List Item")
        },
        secondaryText = {
            Text(text = "List secondaryText List secondaryText")
        },
        singleLineSecondaryText = true,
        overlineText = {
            Text(text = "List overlineText")
        },
        trailing = {
            Icon(imageVector = Icons.Filled.Home, contentDescription = "trailing home")
        }
    )
}
