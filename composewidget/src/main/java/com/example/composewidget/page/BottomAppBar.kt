package com.example.composewidget.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.BottomAppBar
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

/**
 * backgroundColor 背景颜色
    contentColor 内容的颜色
    cutoutShape 形状
    elevation 阴影
    contentPadding 内容边距 可通过PaddingValue设置，也可使用默认AppBarDefaults.ContentPadding
    content 内容控件
 */
@Composable
fun bottomBarView(){
    val posState = remember {
        mutableStateOf(0)
    }
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        backgroundColor = Color.White,
        contentColor = Color.White,
        elevation = 4.dp,
        cutoutShape = RoundedCornerShape(4),
        contentPadding = AppBarDefaults.ContentPadding
    ) {
        Row() {
            // 比如说BottomBar是5个tab
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
}

@Composable
fun tabItemView(pos:Int,modifier: Modifier,posState: MutableState<Int>){
    Column(
        modifier=modifier.clickable {
            posState.value = pos
        }
        ,horizontalAlignment = Alignment.CenterHorizontally
        ,verticalArrangement = Arrangement.Center
    ) {
        val imageVector = when(pos){
            0-> Icons.Filled.Home
            1->Icons.Filled.Message
            2->Icons.Filled.Domain
            3->Icons.Filled.Favorite
            else->Icons.Filled.Person
        }

        val message = when(pos){
            0->"首页"
            1->"消息"
            2->"建筑"
            3->"喜欢"
            else->"我的"
        }

        Icon(imageVector = imageVector, contentDescription = message,tint = if(posState.value == pos) Color.Blue else Color.Black)
        Text(text = message,fontSize = 10.sp,color = if(posState.value == pos) Color.Blue else Color.Black)
    }
}
