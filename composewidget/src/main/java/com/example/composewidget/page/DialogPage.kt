package com.example.composewidget.page

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.composewidget.navigation.Screen.dialog

@Composable
fun DialogPageDemo(){
    val state = remember {
        mutableStateOf(true)
    }
    Dialog(
        onDismissRequest = { state.value = false },
        properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp)
                .background(
                    androidx.compose.ui.graphics.Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "对话框标题",
                color = androidx.compose.ui.graphics.Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "对话框内容,对话框内容,对话框内容,对话框内容,对话框内容,对话框内容",modifier = Modifier.padding(horizontal = 10.dp),lineHeight = 20.sp,fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.height(0.5.dp))
            Row {
               Button(onClick = { state.value = false},modifier = Modifier.weight(1f),shape = RoundedCornerShape(bottomStart = 8.dp),
                   colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.White)
               ) {
                   Text(text = "取消")
               }
                Button(
                    onClick = {
                        state.value = false
                    },
                    modifier = Modifier.weight(1f,true),
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.ui.graphics.Color.White),
                ) {
                    Text(text = "确定")
                }
            }
        }
    }
}