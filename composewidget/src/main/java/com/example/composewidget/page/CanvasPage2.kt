package com.example.composewidget.page

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composewidget.canvas.*
import com.example.composewidget.navigation.Screen
import com.example.composewidget.titleLiveData
import com.example.composewidget.viewmoduel.CanvasViewModule
import com.example.composewidget.viewmoduel.ListViewModule
import com.example.composewidget.widget.FunctionList

@ExperimentalMaterialApi
@Composable
fun CanvasPage2Demo(){
    CanvasDemo()
}

@ExperimentalMaterialApi
@Composable
fun CanvasDemo() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.List.main){
        composable(Screen.CanvasList.main){
            CanvasListContent(navController = navController)
        }
        composable(Screen.CanvasList.drawLine){
            DrawLineDemo()
        }
        composable(Screen.CanvasList.drawRect){
            DrawRectDemo()
        }
        composable(Screen.CanvasList.drawImage){
            DrawImage()
        }
        composable(Screen.CanvasList.drawCircle){
            DrawCircle()
        }
        composable(Screen.CanvasList.drawOval){
            DrawOvalDemo()
        }
        composable(Screen.CanvasList.drawArc){
            DrawArcDemo()
        }
        composable(Screen.CanvasList.drawPath){
            DrawPathDemo()
        }
        composable(Screen.CanvasList.drawPoints){
            drawPointDemo()
        }
        composable(Screen.CanvasList.inset){
            insetDemo()
        }
        composable(Screen.CanvasList.translate){
            translateDemo()
        }
        composable(Screen.CanvasList.rotate){
            rotatePage()
        }
        composable(Screen.CanvasList.rotateRad){
            RotateRabDemo()
        }
        composable(Screen.CanvasList.scale){
            scaleDemo()
        }
        composable(Screen.CanvasList.clipRect){
            clipRectDemo()
        }
        composable(Screen.CanvasList.clipPath){
            clipPathDemo()
        }
        composable(Screen.CanvasList.drawIntoCanvas){
            drawIntoCanvasDemo()
        }
        composable(Screen.CanvasList.withTransform){
            withTransformDemo()
        }
        composable(Screen.CanvasList.drawWithContent){
            drawWithContentDemo()
        }
        composable(Screen.CanvasList.BlendMode){
            BlendModeDemo()
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun CanvasListContent(navController: NavHostController) {
    titleLiveData.value = "Compose Canvas"
    val viewModel: CanvasViewModule = viewModel()
    FunctionList(list = viewModel.functions, navHostController = navController)
}
