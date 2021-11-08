package com.example.composewidget.page

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * AnimateAsState：属性动画
 * updateTransition：多个动画同步
 * AnimateVisibility：可见性动画
 * AnimateContentSize : 布局大小动画
 * Crossfade : 布局切换动画
 */
@Composable
fun AnimationOtherPageDemo(){
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            AnimateAsStateDemo()
            Spacer(Modifier.height(16.dp))
            UpdateTransitionsDemo()
            Spacer(Modifier.height(16.dp))
            AnimateContentSizesDemo()
            Spacer(Modifier.height(16.dp))
            CrossfadesDemo()
        }
    }
}

private sealed class BoxState(val color: Color, val size: Dp) {
    operator fun not() = if (this is Small) Large else Small
    object Small : BoxState(Blue, 64.dp)
    object Large : BoxState(Red, 128.dp)
}

sealed class DemoScene {
    object Text :DemoScene()
    object Icon :DemoScene()
}

@Composable
fun CrossfadesDemo() {
    var scene by remember { mutableStateOf(DemoScene.Text) }

    /*Column(Modifier.padding(16.dp)) {

        Text("AnimateVisibilityDemo")
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            scene = when (scene) {
                DemoScene.Text -> DemoScene.Icon
                else ->  DemoScene.Icon -> DemoScene.Text
            }
        }) {
            Text("toggle")
        }

        Spacer(Modifier.height(16.dp))

        Crossfade(
            targetState = scene
        ) {
            screen ->
            when (scene) {
                is DemoScene.Text ->{
                    Text(text = "Phone", fontSize = 32.sp)
                }

                is DemoScene.Icon ->{
                    Icon(
                        imageVector = Icons.Default.Phone,
                        null,
                        modifier = Modifier.size(48.dp)
                    )
                }

            }
        }

    }*/

}

@Composable
fun AnimateContentSizesDemo() {
    var expend by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("AnimateContentSizeDemo")
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { expend = !expend }
        ) {
            Text(if (expend) "Shrink" else "Expand")
        }
        Spacer(Modifier.height(16.dp))

        Box(
            Modifier
                .background(Color.LightGray)
                .animateContentSize()
        ) {
            Text(
                text = "animateContentSize() animates its own size when its child modifier (or the child composable if it is already at the tail of the chain) changes size. " +
                        "This allows the parent modifier to observe a smooth size change, resulting in an overall continuous visual change.",
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp),
                maxLines = if (expend) Int.MAX_VALUE else 2
            )
        }
    }

}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun UpdateTransitionsDemo() {
    var boxState: BoxState by remember { mutableStateOf(BoxState.Small) }
    val transition = updateTransition(targetState = boxState)

    Column(Modifier.padding(16.dp)) {
        Text("UpdateTransitionDemo")
        Spacer(Modifier.height(16.dp))

        val color by transition.animateColor {
            boxState.color
        }
        val size by transition.animateDp(transitionSpec = {
            if (targetState == BoxState.Large) {
                spring(stiffness = Spring.StiffnessVeryLow)
            } else {
                spring(stiffness = Spring.StiffnessHigh)
            }
        }) {
            boxState.size
        }

        Button(
            onClick = { boxState = !boxState }
        ) {
            Text("Change Color and size")
        }
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .size(size)
                .background(color)
        )
    }


}

@Composable
fun AnimateAsStateDemo() {
    var blue by remember { mutableStateOf(true) }
    val color = if (blue) Blue else Red

    Column(Modifier.padding(16.dp)) {
        Text("AnimateAsStateDemo")
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { blue = !blue }
        ) {
            Text("Change Color")
        }
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .size(128.dp)
                .background(color)
        )
    }

}
