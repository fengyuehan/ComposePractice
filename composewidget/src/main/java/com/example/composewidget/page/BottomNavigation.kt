package com.example.composewidget.page

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("RememberReturnType")
@Composable
fun bottomNavigationTest(){
    val posState = remember {
        mutableStateOf(0)
    }
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        elevation = 6.dp
    ) {
        val modifier = Modifier
            .fillMaxHeight()
            .weight(1f, true)
        tabItemView(0,modifier,posState)
        tabItemView(1,modifier,posState)
        tabItemView(2,modifier,posState)
        tabItemView(3,modifier,posState)
        tabItemView(4,modifier,posState)
    }
}

