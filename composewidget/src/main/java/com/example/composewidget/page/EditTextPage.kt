package com.example.composewidget.page


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.composewidget.titleLiveData

@Composable
fun EditTextPage(){
    titleLiveData.value = "Compose EditText"
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            EditTextDemo()
        }
    }
}

@Composable
fun EditTextDemo() {
    var text1 by remember { mutableStateOf("填充样式的输入框") }
    TextField(value = text1, onValueChange = { text1 = it }, label = { Text("填充样式的输入框") })

    Spacer(modifier = Modifier.height(10.dp))
    var text2 by remember { mutableStateOf("边框样式的输入框") }
    OutlinedTextField(value = text2, onValueChange = { text2 = it }, label = { Text("边框样式的输入框") })
    Spacer(modifier = Modifier.height(10.dp))
    var text4 by remember { mutableStateOf("密码输入框") }

    TextField(
        value = text4,
        onValueChange = {text4 = it },
        label = { Text(text = "密码输入框") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

    Spacer(modifier = Modifier.height(10.dp))
    var text5 by remember { mutableStateOf("") }
    BasicTextField(
        value = text5,
        onValueChange = {
            text5 = it
        },
        decorationBox = {
            innerTextField ->
            if (text5.isEmpty()){
                Box(contentAlignment = Alignment.CenterStart){
                    Text(
                        text = "自定义样式输入框",
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            innerTextField()
        },
        cursorBrush = SolidColor(Color.Red),
        modifier = Modifier.background(Color.LightGray, CircleShape)
            .padding(5.dp,20.dp)
            .fillMaxWidth()

    )

    Spacer(modifier = Modifier.height(10.dp))
    var text6 by remember { mutableStateOf("改了光标颜色的输入框") }
    BasicTextField(
        value = text6,
        onValueChange = { text6 = it },
        cursorBrush = SolidColor(Color.Red),
        modifier = Modifier.height(45.dp),
        textStyle = TextStyle(MaterialTheme.colors.onBackground)
    )

}
