package com.example.composewidget.page

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun DropdownMenuDemo(){
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
        DropdownMenu(
            expanded = expandState.value,
            onDismissRequest = {
                Log.e("ccm","执行了onDismissRequest")
                expandState.value = false
            },
            offset = DpOffset(10.dp,10.dp),
            properties = PopupProperties()
        ) {
            dropdownMenuItemTest(expandState, Icons.Filled.Favorite,"收藏")
            dropdownMenuItemTest(expandState,Icons.Filled.Edit,"编辑")
            dropdownMenuItemTest(expandState,Icons.Filled.Delete,"删除")
        }
    }
}

@Composable
fun dropdownMenuItemTest(state: MutableState<Boolean>, icon: ImageVector, text:String){
    val interactionSource = remember { MutableInteractionSource() }
    val pressState = interactionSource.collectIsPressedAsState()
    val focusState = interactionSource.collectIsFocusedAsState()
    DropdownMenuItem(
        onClick = {
            state.value = false
        },
        enabled = true,
        interactionSource = interactionSource
    ) {
        Icon(imageVector = icon, contentDescription = text,tint = if(pressState.value || focusState.value) Color.Red else Color.Black)
        Text(text = text,modifier = Modifier.padding(start = 10.dp),color = if(pressState.value || focusState.value) Color.Red else Color.Black)
    }
}
