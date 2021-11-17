package com.example.composewidget.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.unit.Constraints

@Composable
fun RowPageTest(modifier: Modifier = Modifier,content:@Composable () -> Unit){
    val measurePolicy = MeasurePolicy{ measurables, constraints ->  
        val placeables = measurables.map {
            //测量子孩子，每个child只能测量一次，不能多次测量 child 控制 child 宽高在约束constraints范围内
            child -> child.measure(constraints)
        }
        //记录下一个要放置的child 的x值
        var xPosition = 0
        //参考测量结果，决定布局的大小，这里没有处理直接用constraints的最大值
        layout(constraints.minWidth,constraints.minHeight){
            placeables.forEach{
                placeable ->
                //放置每一个 child
                //4️⃣ placeRelative 此方法必须在Placeable.PlacementScope 作用域下调用
                placeable.placeRelative(xPosition,0)
                //按水平方向依次排列
                xPosition += placeable.width
            }
        }

    }
    //把对应的参数放到Layout函数中
    Layout(measurePolicy = measurePolicy,content = content,modifier = modifier)
}

@Composable
fun RowPageTest1(modifier: Modifier = Modifier,content:@Composable () -> Unit){
    val measurePolicy = MeasurePolicy{ measurables, constraints ->
        val placeables = measurables.map {
            //测量子孩子，每个child只能测量一次，不能多次测量 child 控制 child 宽高在约束constraints范围内
                child -> child.measure(Constraints(0,constraints.maxWidth,0,constraints.maxHeight))
        }
        //记录下一个要放置的child 的x值
        var xPosition = 0
        //参考测量结果，决定布局的大小，这里没有处理直接用constraints的最大值
        layout(constraints.minWidth,constraints.minHeight){
            placeables.forEach{
                    placeable ->
                //放置每一个 child
                //4️⃣ placeRelative 此方法必须在Placeable.PlacementScope 作用域下调用
                placeable.placeRelative(xPosition,0)
                //按水平方向依次排列
                xPosition += placeable.width
            }
        }

    }
    //把对应的参数放到Layout函数中
    Layout(measurePolicy = measurePolicy,content = content,modifier = modifier)
}

@Composable
fun RowPageTest2(modifier: Modifier = Modifier,content:@Composable () -> Unit){
    val measurePolicy = MeasurePolicy{ measurables, constraints ->
        var childrenSpace = 0
        val placeables = measurables.mapIndexed {
            index,child ->
            //测量子孩子，每个child只能测量一次，不能多次测量 child 控制 child 宽高在约束constraints范围内
                val placeable  =  child.measure(
                    Constraints(0,constraints.maxWidth - childrenSpace,0,constraints.maxHeight)
                )
            childrenSpace += placeable.width
            placeable
        }
        //记录下一个要放置的child 的x值
        var xPosition = 0
        //参考测量结果，决定布局的大小，这里没有处理直接用constraints的最大值
        layout(constraints.minWidth,constraints.minHeight){
            placeables.forEach{
                    placeable ->
                //放置每一个 child
                //4️⃣ placeRelative 此方法必须在Placeable.PlacementScope 作用域下调用
                placeable.placeRelative(xPosition,0)
                //按水平方向依次排列
                xPosition += placeable.width
            }
        }

    }
    //把对应的参数放到Layout函数中
    Layout(measurePolicy = measurePolicy,content = content,modifier = modifier)
}