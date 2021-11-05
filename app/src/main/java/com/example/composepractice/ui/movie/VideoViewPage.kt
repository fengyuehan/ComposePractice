package com.example.composepractice.ui.movie

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cn.jzvd.JzvdStd
import com.example.composepractice.ext.toPx

@Composable
fun VideoViewPage(url:String,title:String,click:() -> Unit){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {
        AndroidView(factory = {
            val jzvdStd = JzvdStd(context)
            jzvdStd.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200.toPx())
            jzvdStd
        },modifier = Modifier.align(Alignment.Center)){
            it.setUp(url,title)
            it.startVideoAfterPreloading()
        }
        BackArrowDown(click = click)
    }
}

@Composable
fun BackArrowDown(click: () -> Unit){
    Surface(
        shape = CircleShape,
        modifier = Modifier
            .padding(15.dp, 35.dp, 0.dp, 0.dp)
            .size(24.dp),
        color = Color.Gray
    ) {
        Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.clickable {
                click()
            })
    }
}