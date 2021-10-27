package com.example.composewidget.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composewidget.titleLiveData
import com.example.composewidget.widget.RadioButton

@Composable
fun AnimationPageDemo(){
    titleLiveData.value = "AnimationPageDemo"
    AnimationContent()
}

@Composable
fun AnimationContent() {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            HighLevelAnimation()
            LowLevelAnimation()
        }
    }
}

@Composable
fun LowLevelAnimation() {
    Spacer(modifier = Modifier.height(20.dp))
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Text("--------低级别动画API-------")
    }

    AnimateFloatAsStateDemo()
    AnimatableDemo()
    UpdateTransitionDemo()
    RememberInfiniteTransitionDemo()
    SpringDemo()
    TweenDemo()

    Spacer(modifier = Modifier.height(100.dp))
}

@Composable
fun TweenDemo() {
    Spacer(modifier = Modifier.height(60.dp))
    var enabled by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(300) }
    var easingType by remember { mutableStateOf(LinearEasing) }
    val offsetX: Int by animateIntAsState(
        if (enabled) 200 else 0,
        animationSpec = tween(durationMillis = duration, easing = easingType)
    )
    Row {
        Button(onClick = { enabled = !enabled }) {
            Text("Animate with tween")
        }
        Text("offsetX=${offsetX}")
    }
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        Modifier
            .size(10.dp)
            .offset(offsetX.dp, 0.dp)
            .background(MaterialTheme.colors.primary)
    )
    Spacer(modifier = Modifier.height(20.dp))
    RadioButton(
        isSelected = easingType == LinearEasing,
        text = "LinearEasing"
    ) {
        easingType = LinearEasing
    }
    RadioButton(
        isSelected = easingType == FastOutSlowInEasing,
        text = "FastOutSlowInEasing"
    ) {
        easingType = FastOutSlowInEasing
    }
    RadioButton(
        isSelected = easingType == LinearOutSlowInEasing,
        text = "LinearOutSlowInEasing"
    ) {
        easingType = LinearOutSlowInEasing
    }
    RadioButton(
        isSelected = easingType == FastOutLinearInEasing,
        text = "FastOutLinearInEasing"
    ) {
        easingType = FastOutLinearInEasing
    }
    val cubicBezierEasing = CubicBezierEasing(0f, 0.2f, 0.8f, 1f)
    RadioButton(
        isSelected = easingType == cubicBezierEasing,
        text = "CubicBezierEasing(自定义）"
    ) {
        easingType = cubicBezierEasing
    }
    OutlinedTextField(
        value = duration.toString(),
        onValueChange = { duration = if (it.isNotEmpty()) it.toInt() else 300 },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("duration 动画时间") })
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun SpringDemo() {
    Spacer(modifier = Modifier.height(60.dp))
    var enabled by remember { mutableStateOf(false) }
    var dampingRatio by remember { mutableStateOf(Spring.DampingRatioNoBouncy) }
    var stiffness by remember { mutableStateOf(Spring.StiffnessVeryLow) }
    val offsetX: Int by animateIntAsState(
        if (enabled) 200 else 0, animationSpec = spring(dampingRatio, stiffness)
    )
    Row {
        Button(onClick = { enabled = !enabled }) {
            Text("Animate with spring")
        }
        Text("offsetX=${offsetX}")
    }
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        Modifier
            .size(10.dp)
            .offset(offsetX.dp, 0.dp)
            .background(MaterialTheme.colors.primary)
    )
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedTextField(
        value = dampingRatio.toString(),
        onValueChange = { dampingRatio = if (it.isNotEmpty()) it.toFloat() else 0.2f },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("dampingRatio 弹性系数") })
    OutlinedTextField(
        value = stiffness.toString(),
        onValueChange = { stiffness = if (it.isNotEmpty()) it.toFloat() else 0.2f },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("stiffness 移动速度") })
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun RememberInfiniteTransitionDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = "rememberInfiniteTransition用于一直执行动画")
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        Modifier
            .size(100.dp)
            .offset(offset.dp, 0.dp)
            .background(color)
    )
}

@Composable
fun UpdateTransitionDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("updateTransition:组合多种动画")
    var state by remember { mutableStateOf(false) }
    val transition = updateTransition(state, label = "")
    val color by transition.animateColor {
        if (it) Color.Red else MaterialTheme.colors.primary
    }
    val offset by transition.animateIntOffset {
        if (it) IntOffset(100, 100) else IntOffset(0, 0)
    }
    Row {
        Button(onClick = { state = !state }) {
            Text("updateTransition")
        }
        Text(
            "color=${
                color.value.toString(16).subSequence(0, 8)
            }   \noffsetX=${offset.x}  offsetY=${offset.y}"
        )
    }
    Box(
        Modifier
            .size(100.dp)
            .offset { offset }
            .background(color)
    )
}

@Composable
fun AnimatableDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("Animatable:给定初始值，使用animateTo(targetValue)过渡到结束值")
    var enabledAnimatable by remember { mutableStateOf(false) }
    val translationX = remember { androidx.compose.animation.core.Animatable(0f) }
    //LaunchedEffect意思为在某个可组合项的作用域内运行挂起函数,LaunchedEffect是一个可组合函数，所以只能在可组合函数内使用
    //suspend fun animateTo是一个挂起函数，所以需要使用LaunchedEffect
    //假如我们需要在可组合函数外启动协程，在可组合函数声明周期结束时候取消，可以使用rememberCoroutineScope。另外rememberCoroutineScope还可以控制多个协程。
    //有时候需要在组合函数变化或者退出时候做些处理，可以使用DisposableEffect。
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mviViewModel = MviViewModel()
        setContent {
            BackHandler(onBackPressedDispatcher){//该代码是在Activity中使用
                Log.e("YM","LandingScreen事件已过")
            }
        }

    }
    @Composable
    fun BackHandler(backDispatcher: OnBackPressedDispatcher, onBack: () -> Unit) {

        // Safely update the current `onBack` lambda when a new one is provided
        val currentOnBack by rememberUpdatedState(onBack)

        // Remember in Composition a back callback that calls the `onBack` lambda
        val backCallback = remember {
            // Always intercept back events. See the SideEffect for
            // a more complete version
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentOnBack()
                }
            }
        }

        // If `backDispatcher` changes, dispose and reset the effect
        DisposableEffect(backDispatcher) {
            // Add callback to the backDispatcher
            backDispatcher.addCallback(backCallback)

            // When the effect leaves the Composition, remove the callback
            onDispose {//组合被移除时会调用这个函数
                backCallback.remove()
            }
        }
    }*/

    LaunchedEffect(enabledAnimatable) {
        if (enabledAnimatable) translationX.animateTo(100f) else translationX.animateTo(0f)
    }
    Row() {
        Button(onClick = { enabledAnimatable = !enabledAnimatable }) {
            Text("Animatable")
        }
        Text("offset=${translationX.value}")
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(
        Modifier
            .size(100.dp)
            .offset(translationX.value.dp, 0.dp)
            .background(MaterialTheme.colors.primary)
    )
}

@Composable
fun AnimateFloatAsStateDemo() {
    Spacer(modifier = Modifier.height(10.dp))
    Text("animateFloatAsState:给定初始值和结束值，展示动画")
    var enabled by remember { mutableStateOf(false) }
    val scale: Float by animateFloatAsState(if (enabled) 1f else 0.5f)
    Row {
        Button(onClick = { enabled = !enabled }) {
            Text("animateFloatAsState")
        }
        Text("scaleX=${scale}  scaleY=$scale")
    }
    Box(
        Modifier
            .size(100.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .background(MaterialTheme.colors.primary)
    )
}

@Composable
fun HighLevelAnimation() {
    Spacer(modifier = Modifier.height(20.dp))
    Row(horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth()) {
        Text(text = "--------高级别动画API-------")
    }
    AnimatedVisibilityDemo()
    AnimateContentSizeDemo()
    CrossfadeDemo()
}

@Composable
fun CrossfadeDemo() {
    var currentPage by remember {
        mutableStateOf("A")
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
       Button(onClick = { currentPage = if (currentPage == "A") "B" else "A" }) {
           Text(text = "CrossfadeDemo")
       }
        Text(text = "currentPage$currentPage",textAlign = TextAlign.Center)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Crossfade(targetState = currentPage) {
        screen ->
        when(screen){
            "A" -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(10.dp)
                        .size(100.dp)
                )
            }
            "B" -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colors.onBackground)
                        .padding(10.dp)
                        .size(100.dp)
                )
            }
        }
    }
}

@Composable
fun AnimateContentSizeDemo() {
    var size by remember {
        mutableStateOf(100.dp)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Text("AnimateContentSize:尺寸该变时添加动画过渡")
    Row {
       Button(onClick = {size = if (size == 100.dp) 150.dp else 100.dp}) {
           Text(text = "AnimateContentSize")
       }
        Text(text = "size$size",textAlign = TextAlign.Center)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Box(modifier = Modifier
        .background(MaterialTheme.colors.primary)
        .animateContentSize()
        .padding(10.dp)
        .size(size = size))
}

@Composable
fun AnimatedVisibilityDemo() {
    var visible by remember {
        mutableStateOf(false)
    }
    Spacer(modifier = Modifier.height(10.dp))
    Row {
       Button(onClick = { visible = !visible }) {
           Text(text = "AnimatedVisibility")
       }
        Spacer(modifier = Modifier.width(40.dp))
        Text(
            text = "isVisible = $visible",
            textAlign = TextAlign.Center
        )
        AnimatedVisibility(visible = visible,modifier = Modifier.padding(10.dp)) {
            Box(modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .size(100.dp))
        }
    }
}
