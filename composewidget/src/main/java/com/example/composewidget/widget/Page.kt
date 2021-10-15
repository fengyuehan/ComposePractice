package com.example.composewidget.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composewidget.ui.theme.ThemeManager
import com.example.composewidget.ui.theme.ThemeType

@Composable
fun Page(
    title:String,
    themeType: ThemeType,
    isDarkTheme:Boolean,
    leftIcon :ImageVector ?= null,
    rightIcon:ImageVector ?= null,
    onLeftClick:()-> Unit = {},
    onRightClick:()->Unit = {},
    content: @Composable () -> Unit
){
    ThemeManager.WithTheme(
        type = themeType,
        isDarkTheme
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                AppBar(
                    title = title,
                    leftIcon = leftIcon,
                    onLeftClick = onLeftClick,
                    rightIcon = rightIcon,
                    onRightClick = onRightClick
                )
                content()
            }
        }
    }
}