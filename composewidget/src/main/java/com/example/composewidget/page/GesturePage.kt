package com.example.composewidget.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composewidget.titleLiveData
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun GesturePageDemo(){
    titleLiveData.value = "GesturePageDemo()"
    GestureContent()
}

@ExperimentalMaterialApi
@Composable
fun GestureContent() {
    Box(modifier = Modifier.fillMaxSize()){
        GestureDemo()
    }
}

@ExperimentalMaterialApi
@Composable
fun GestureDemo() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ClickableDemo()
        DoubleClickableDemo()
        LongPressDemo()
        SingleClickDemo()
        HorizontalDragDemo()
        VerticalDragDemo()
        DragDemo()
        TouchPositionDemo()
        SwipeableDemo()
        swipeableSample2()
        TransformableDemo()
        TransformableDemo1()
        TransformableDemo2()
        TransformableDemo3()
        NestedBoxDemo()
    }
}

/**
 * PointerEventPass.Initial	本组件优先处理手势，处理后交给子组件
PointerEventPass.Main	若子组件为Final，本组件优先处理手势。否则将手势交给子组件处理，结束后本组件再处理。
PointerEventPass.Final	若子组件也为Final，本组件优先处理手势。否则将手势交给子组件处理，结束后本组件再处理。
大家可能觉得 Main 与 Final 是等价的。但其实两者在作为子组件时分发顺序会完全不同，举个例子。
当父组件为Final，子组件为Main时，事件分发顺序： 子组件 -> 父组件
当父组件为Final，子组件为Final时，事件分发顺序： 父组件 -> 子组件
 */

@Composable
fun NestedBoxDemo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(600.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    awaitPointerEvent(PointerEventPass.Initial)
                    Log.d("compose_study", "first layer")
                }
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(400.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        awaitPointerEvent(PointerEventPass.Final)
                        Log.d("compose_study", "second layer")
                    }
                }
        ) {
            Box(
                Modifier
                    .size(200.dp)
                    .background(Color.Green)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            awaitPointerEvent()
                            Log.d("compose_study", "third layer")
                        }
                    }
            )
        }
    }
}

@Composable
fun TransformableDemo3() {
    var zoom by remember { mutableStateOf(1f) }
    var angle by remember { mutableStateOf(0f) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)){
        Box(
            Modifier
                .rotate(angle)
                .scale(zoom)
                .offset {
                    IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt())
                }
                .background(Color.Blue)
                .pointerInput(Unit) {
                    forEachGesture {
                    /*awaitPointerEvent	手势事件
                    awaitFirstDown	第一根手指的按下事件
                    drag	拖动事件
                    horizontalDrag	水平拖动事件
                    verticalDrag	垂直拖动事件
                    awaitDragOrCancellation	单次拖动事件
                    awaitHorizontalDragOrCancellation	单次水平拖动事件
                    awaitVerticalDragOrCancellation	单次垂直拖动事件
                    awaitTouchSlopOrCancellation	有效拖动事件
                    awaitHorizontalTouchSlopOrCancellation	有效水平拖动事件
                    awaitVerticalTouchSlopOrCancellation	有效垂直拖动事件*/
                        awaitPointerEventScope {
                            awaitFirstDown()

                            /*
                            suspend fun awaitPointerEvent(
                                pass: PointerEventPass = PointerEventPass.Main
                            ): PointerEvent
                            PointerEventPass.Initial	本组件优先处理手势，处理后交给子组件
                            PointerEventPass.Main	若子组件为Final，本组件优先处理手势。否则将手势交给子组件处理，结束后本组件再处理。
                            PointerEventPass.Final	若子组件也为Final，本组件优先处理手势。否则将手势交给子组件处理，结束后本组件再处理。
                            */
                            do {
                                val event = awaitPointerEvent()
                                val offset = event.calculatePan()
                                offsetX.value += offset.x
                                offsetY.value += offset.y
                                val rotation = event.calculateRotation()
                                angle += rotation
                                zoom *= event.calculateZoom()
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
                .size(100.dp)
        )
    }

}

/**
 * 修复后的
 */
@Composable
fun TransformableDemo2() {
    Text("缩放，平移，旋转 ")
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        //不要使用此处的offsetChange
    }
    Box(
        Modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    // 返回正确的移动位置，跟随手指
                    offset += dragAmount
                }
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y,
            )
            .transformable(state = state)
            .background(Color.Blue)
            .fillMaxSize()
    )
}

@ExperimentalMaterialApi
@Composable
fun swipeableSample2() {

    val width = 350.dp
    val squareSize = 50.dp

    val swipeableState = rememberSwipeableState("A")
    val sizePx = with(LocalDensity.current) { (width - squareSize).toPx() }
    val anchors = mapOf(0f to "A", sizePx / 2 to "B", sizePx to "C")

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.Black)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(swipeableState.currentValue, color = Color.White, fontSize = 24.sp)
        }
    }
}

/**
 * 缩放平移之后，中心点不是缩放平移后的中心点，看官方示例运行的效果，很明显双指移动的时候不跟随手指
 */
@Composable
fun TransformableDemo1() {
    Text("缩放，平移，旋转 ")
    var boxSize = 100.dp
    var offset by remember { mutableStateOf(Offset.Zero) }
    var ratationAngle by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        /*关于偏移、缩放与旋转，我们建议的调用顺序是 rotate -> scale -> offset
        若offset发生在rotate之前时，rotate会对offset造成影响。具体表现为当出现拖动手势时，组件会以当前角度为坐标轴进行偏移。
        若offset发生在scale之前是，scale也会对offset造成影响。具体表现为UI组件在拖动时不跟手
        */
        Box(Modifier
            .size(boxSize)
            .rotate(ratationAngle) // 需要注意offset与rotate的调用先后顺序
            .scale(scale)
            .offset {
                IntOffset(offset.x.roundToInt(), offset.y.roundToInt())
            }
            .background(Color.Green)
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = true, // 平移或放大时是否可以旋转
                    onGesture = { centroid: Offset, pan: Offset, zoom: Float, rotation: Float ->
                        offset += pan
                        scale *= zoom
                        ratationAngle += rotation
                    }
                )
            }
        )
    }
}

@Composable
fun TransformableDemo() {
    Text("缩放，平移，旋转 ")
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, rotationChanged ->
                        scale *= zoom
                        rotation += rotationChanged
                        offset += pan
                    }
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offset = Offset(dragAmount.x, dragAmount.y)
                    }
                }
                .background(MaterialTheme.colors.primary)
                .size(100.dp)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun SwipeableDemo() {
    Text("Swipeable ")
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    //dp转化成px
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(MaterialTheme.colors.secondary),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(MaterialTheme.colors.onBackground)
        )
    }
}
@Composable
fun TouchPositionDemo() {
    var touchedX by remember { mutableStateOf(0f) }
    var touchedY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consumeAllChanges()
                    touchedX = change.position.x
                    touchedY = change.position.y
                }
            }, contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "这是一个监听触摸位置的组件")
            Text(text = "touchedX=${touchedX.toInt()}   touchedY=${touchedY.toInt()}")
        }
    }
}

@Composable
fun  DragDemo() {
    Text("任意拖动")
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(MaterialTheme.colors.primary)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

@Composable
fun VerticalDragDemo() {
    Text("垂直拖动")
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(500.dp),
        contentAlignment = Alignment.Center){
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset { IntOffset(0, offsetY.roundToInt()) }
                .background(MaterialTheme.colors.primary)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        offsetY += delta
                    }
                )
        )
    }
}

@Composable
fun HorizontalDragDemo() {
    Text("水平拖动")
    var offsetX by remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier
            .size(100.dp)
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .background(MaterialTheme.colors.primary)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            )
        )
    }
}

/*onPress 普通按下事件

onDoubleTap 前必定会先回调 2 次 Press

onLongPress 前必定会先回调 1 次 Press（时间长）

onTap 前必定会先回调 1 次 Press（时间短）*/


@Composable
fun SingleClickDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("单击，双击，长按，按下事件监听")
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(MaterialTheme.colors.primary)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        Toast
                            .makeText(context, "单击事件", Toast.LENGTH_LONG)
                            .show()
                    }
                )
            }
    )
    Text("Clicked me", color = MaterialTheme.colors.onSurface)
}

@Composable
fun LongPressDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("单击，双击，长按，按下事件监听")
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(MaterialTheme.colors.primary)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        Toast
                            .makeText(context, "长按事件", Toast.LENGTH_LONG)
                            .show()
                    }
                )
            }
    )
    Text("Clicked me", color = MaterialTheme.colors.onSurface)
}

@Composable
fun DoubleClickableDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("单击，双击，长按，按下事件监听")
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(MaterialTheme.colors.primary)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        Toast
                            .makeText(context, "双击事件", Toast.LENGTH_LONG)
                            .show()
                    }
                )
            }
    )
    Text("Clicked me", color = MaterialTheme.colors.onSurface)
}

@Composable
fun ClickableDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "点击事件监听")
    var clickCount by remember {
        mutableStateOf(0)
    }
    Button(onClick = { clickCount++ }) {
        Text(text = "Clicked $clickCount")
    }
}
