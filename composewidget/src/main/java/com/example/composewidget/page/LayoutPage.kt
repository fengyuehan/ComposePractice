package com.example.composewidget.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.composewidget.titleLiveData

/**
 * Column 纵向排列布局、
 * Row 横向排列布局、
 * Box 堆叠排列布局
 */
@ExperimentalUnitApi
@Composable
fun LayoutPage(){
    titleLiveData.value = "Compose Layout"
    LayoutContent()
}

@ExperimentalUnitApi
@Composable
fun LayoutContent() {
    /**
     * mutableStateOf
     * 会给变量赋予监听数值变化的能力，从而会触发使用该值的View进行重绘。
     * remember
     * remember 在 mutableStateOf 之上又增加了一层内容：把这个变量的值存储脱离函数，即这个函数再次执行这个值并不会变成初始值。（页面切换 会失效）
     * rememberSaveable
     * rememberSaveable 在remember 上保证了可以在页面切换的过程中保存数据。
     */

    /**
     * var clickCount by remember{mutableStateOf(0)}
     * var clickCount = remember{mutableStateOf(0)}
     *
     * 用by的话，count是Int类型【即mutableStateOf参数的类型】
     * 用“=”， count是MutableState类型，使用时需要通过.value来取值
     * 使用by的时候可能会报红，错误是 Type 'TypeVariable(T)' has no method 'getValue(Nothing?, KProperty<*>)' and thus it cannot serve as a delegate
     * 解决办法：import androidx.compose.runtime.*
     *
     */

    /**
     * Column、Row、Box之类的组件，传入的Composable参数是给对应的Scope的扩展函数，而Surface接收的就是一个普通的Composable函数，一个可能不够准确的结论是：
     * 扩展函数中的直接组件重绘的话，相当于接收这个扩展函数的组件也要重绘。【即Button需要重绘，Column也会重绘】
     */
    var clickCount by remember {
        mutableStateOf(0)
    }
    /**
     *  Scaffold 组件的功能就跟它的翻译一样，用于构建一个基本的 Material Design 布局框架。它提供了诸如 TopAppBar、BottomAppBar、
     *  FloatingActionButton 和 Drawer 等常见的组件。
     */
    Scaffold(
        Modifier.fillMaxSize(),
        drawerContent = {
            Text(text = "这是drawerContent")
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        MaterialTheme.colors.primary,
                        CircleShape
                    )
                    .clickable {
                        clickCount++
                    },
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = "Action\nButton",
                    color = Color.White,
                    fontSize = TextUnit(10f, TextUnitType.Sp),
                    textAlign = TextAlign.Center
                )
            }
        },
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
                RowDemo()
                ColumnDemo()
                BoxDemo()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "试试向右滑动")
                }
                Text(
                    "第${clickCount}次点击",
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "这个界面使用的是Scaffold来布局",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        })
}

/**
 * Box可以理解为FrameLayout
 */
@Composable
fun BoxDemo() {
    Box(modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 0.dp)) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFDD26AF)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .background(Color(0xFF1BC3E0)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color(0xFFDB4B4B)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "这是Box", modifier = Modifier.padding(10.dp), color = Color.White)
                }
            }
        }
    }
}

/**
 * Column相当于线性垂直布局
 */
@Composable
fun ColumnDemo() {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(0.dp, 30.dp, 0.dp, 0.dp)
    ) {
        Column() {
            Box(
                modifier = Modifier
                    .background(Color(0xFFDB4B4B))
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF3587EC))
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFFDD26AF))
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF1BC3E0))
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "这是Column",
                modifier = Modifier
                    .padding(10.dp),
                color = Color.White
            )
        }
    }
}

/**
 * Box可以理解为FrameLayout
 * Row相当于线性水平布局
 */
@Composable
fun RowDemo() {
    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Row {
           Box(
               modifier = Modifier
                   .background(Color(0xFFCC3939))
                   .width(100.dp)
                   .height(50.dp)
           )
            Box(
                modifier = Modifier
                    .background(Color(0xFF3587EC))
                    .width(100.dp)
                    .height(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFFDD26AF))
                    .width(100.dp)
                    .height(50.dp)
            )
            Box(
                modifier = Modifier
                    .background(Color(0xFF1BC3E0))
                    .width(100.dp)
                    .height(50.dp)
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "这是Row",
                modifier = Modifier
                    .padding(10.dp),
                color = Color.White
            )
        }
    }
}
