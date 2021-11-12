package com.example.composewidget.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.composewidget.navigation.Screen.Popup

@Composable
fun PopPageDemo(){
    val expandState = remember {
        mutableStateOf(false)
    }
    Column() {
        Button(
            onClick = {
                expandState.value = true
            }) {
            Text(text = "打开 DropdownMenu")
        }
        Popup(
            alignment = Alignment.TopStart,
            onDismissRequest = {
                Log.e("ccm","执行了onDismissRequest")
                expandState.value = false
            },
            offset = IntOffset(10,140),
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Min).shadow(
                    elevation = 2.dp,shape = RoundedCornerShape(3.dp)
                ).background(Color.White,shape = RoundedCornerShape(3.dp))
            ) {
                dropdownMenuItemTest(expandState, Icons.Filled.Favorite,"收藏")
                dropdownMenuItemTest(expandState,Icons.Filled.Edit,"编辑")
                dropdownMenuItemTest(expandState,Icons.Filled.Delete,"删除")
            }
        }
    }


}