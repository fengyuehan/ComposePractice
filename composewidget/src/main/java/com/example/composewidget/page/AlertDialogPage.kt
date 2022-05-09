package com.example.composewidget.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun AlertDialogDemo(){
    AlertDialog(
        onDismissRequest = { },
        buttons = {
            Row {
                Button(
                    onClick = {
                    },
                    modifier = Modifier.weight(1f,true),
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = {
                    },
                    modifier = Modifier.weight(1f,true),
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "确定")
                }
            }
        },
        title = {
            Text(text = "对话框标题")
        },
        text = {
            Text(text = "对话框内容对话框内容")
        },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        properties = DialogProperties()
    )

}