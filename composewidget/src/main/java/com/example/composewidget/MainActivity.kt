package com.example.composewidget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composewidget.navigation.Screen
import com.example.composewidget.page.*


import com.example.composewidget.ui.theme.ThemeManager
import com.example.composewidget.ui.theme.ThemeType
import com.example.composewidget.viewmoduel.MainViewModel
import com.example.composewidget.widget.FunctionList
import com.example.composewidget.widget.Page

val titleLiveData = MutableLiveData<String>()
val themeTypeState = mutableStateOf(ThemeType.Default)
val darkThemeState = mutableStateOf(false)

class MainActivity : ComponentActivity() {
    @ExperimentalTextApi
    @ExperimentalUnitApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val rememberTitle:String by titleLiveData.observeAsState("Compose Demo")
            val themeType:ThemeType by themeTypeState
            val isDarkTheme :Boolean by darkThemeState
            val wrappedColor = ThemeManager.getWrappedColor(themeType)
            window.statusBarColor = if(isDarkTheme) {
                Color(0xFF181818)
            }else {
                wrappedColor.lightColors.primary
            }.toArgb()
            Page(
                title = rememberTitle,
                themeType = themeType,
                isDarkTheme = isDarkTheme
            ){
                Content()
            }
        }
    }
}

@ExperimentalTextApi
@ExperimentalUnitApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Content(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.main){
        composable(Screen.main){
            Main(navController = navController)
        }
        composable(Screen.layout){
            LayoutPage()
        }
        composable(Screen.remember){
            RememberPage()
        }
        composable(Screen.text){
            TextPageDemo()
        }
        composable(Screen.editText){
            EditTextPage()
        }
        composable(Screen.image){
            ImagePageDemo()
        }
        composable(Screen.List.main){
            ListPageDemo()
        }
        composable(Screen.animation){
            AnimationPageDemo()
        }
        composable(Screen.gesture){
            GesturePageDemo()
        }
        composable(Screen.canvas){
            CanvasPageDemo()
        }
        composable(Screen.theme){
            ThemePageDemo()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Main(navController: NavHostController) {
    titleLiveData.value = "Compose Demo"
    val viewModel: MainViewModel = viewModel()
    FunctionList(list = viewModel.functions, navHostController = navController)
}

@Composable
fun ButtonComponent(context: Context, intent: Intent, buttonText:String){
    Button(
        onClick = {
            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = buttonText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

