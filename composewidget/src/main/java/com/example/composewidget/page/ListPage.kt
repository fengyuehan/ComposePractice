package com.example.composewidget.page

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import com.example.composewidget.R
import com.example.composewidget.navigation.Screen
import com.example.composewidget.titleLiveData
import com.example.composewidget.viewmoduel.ListViewModule
import com.example.composewidget.viewmoduel.MainViewModel
import com.example.composewidget.widget.FunctionList
import com.example.composewidget.widget.HorizontalNoMoreItem
import com.example.composewidget.widget.VerticalNoMoreItem
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListPageDemo(){
    titleLiveData.value = "List Demo"
    ListDemo()
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ListDemo() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.List.main){
        composable(Screen.List.main){
            ListContent(navController)
        }
        composable(Screen.List.scrollable_row){
            ScrollableRowDemo()
        }
        composable(Screen.List.scrollable_column){
            ScrollableColumnDemo()
        }
        composable(Screen.List.lazy_row){
            LazyRowDemo()
        }
        composable(Screen.List.lazy_column){
            LazyColumnDemo()
        }
        composable(Screen.List.sticky_header){
            StickerHeaderDemo()
        }
        composable(Screen.List.lazy_vertical_grid){
            LazyVerticalGridDemo()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun LazyVerticalGridDemo() {
    titleLiveData.value = "LazyVerticalGridDemo"
    val viewModel:MainViewModel = viewModel()
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp),modifier = Modifier.fillMaxWidth()){
        items(viewModel.images + viewModel.images + viewModel.images){
            image -> ImageCard(image = image, modifier = Modifier.size(128.dp), text = "這是LazyVerticalGridDemo")
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun StickerHeaderDemo() {
    titleLiveData.value = "StickerHeaderDemo"
    val viewModel:MainViewModel = viewModel()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)){
        item {
            ImageCard(image = viewModel.images[0], modifier = Modifier
                .fillMaxWidth()
                .height(300.dp), text = "這是StickerHeaderDemo")
        }
        stickyHeader {
            Column(modifier = Modifier
                .height(50.dp)
                .background(MaterialTheme.colors.background)) {
                Text(text = "我是粘性標題",modifier = Modifier.fillMaxWidth(),textAlign = TextAlign.Center)
            }
        }
        items(viewModel.images,key = {
            it
        }){
            image -> ImageCard(image = image, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), text = "這是StickerHeaderDemo")
        }
        item {
            VerticalNoMoreItem()
        }
    }
}

@Composable
fun LazyColumnDemo() {
    titleLiveData.value = "LazyColumnDemo"
    val viewModel:MainViewModel = viewModel()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)){
        items(items = viewModel.images,key = {
            it
        }){
            image -> ImageCard(image = image, modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), text = "這是LazyColumnDemo")
        }
        item {
            VerticalNoMoreItem()
        }
    }
}

@Composable
fun LazyRowDemo() {
    titleLiveData.value = "LazyRowDemo"
    val viewModel:MainViewModel = viewModel()
    LazyRow(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.spacedBy(15.dp)){
        items(
            items = viewModel.images,
            key = {
                it
            }){
            image -> ImageCard(image = image, modifier = Modifier.size(200.dp,160.dp), text = "這是LazyRowDemo")
        }
        item {
            HorizontalNoMoreItem()
        }
    }
}

@Composable
fun ScrollableColumnDemo() {
    titleLiveData.value = "ScrollableColumnDemo"
    val viewModel:MainViewModel = viewModel()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        for (image in viewModel.images){
            ImageCard(
                image = image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                text ="這是ScrollableColumnDemo"
            )
        }
    }
}

@Composable
fun ScrollableRowDemo() {
    titleLiveData.value = "ScrollableRowDemo"
    val viewModule:MainViewModel = viewModel()
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {
            for (image in viewModule.images){
                ImageCard(image = image, modifier = Modifier.size(200.dp,160.dp), text = "這是ScrollableRowDemo" )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListContent(navController: NavHostController) {
    titleLiveData.value = "Compose List"
    val viewModel:ListViewModule = viewModel()
    FunctionList(list = viewModel.functions, navHostController = navController)
}

@Composable
fun ImageCard(image:String,modifier: Modifier,text:String){
    Box(modifier = modifier,contentAlignment = Alignment.Center){
        Image(painter = rememberCoilPainter(request = image,fadeIn = true),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            /**
             * 图片缩放设置,和原生的ImageView的scaleType属性类似,取值是ContentScale的枚举,默认是ContentScale.Fit(即自适应)
             * ContentScale.Fit(即自适应)
             * ContentScale.Crop 裁剪
             * ContentScale.FillBounds 拉伸图片宽高填满形状
             * ContentScale.FillHeight 拉伸图片高度填满高度
             * ContentScale.FillWidth 拉伸图片宽填满宽度
             * ContentScale.None 不缩放
             */
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun loadGif(image:String){
    Column {
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context = context)
            .componentRegistry{
                add(GifDecoder())
            }
            .build()
        Image(
            painter = rememberImagePainter(
                data = image,
                imageLoader = imageLoader,
                builder = {
                    crossfade(true)
                    placeholder(R.mipmap.ic_launcher)
                }
            ),
            contentDescription = null)
    }
}
