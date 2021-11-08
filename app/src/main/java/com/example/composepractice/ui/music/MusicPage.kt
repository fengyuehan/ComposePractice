package com.example.composepractice.ui.music

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composepractice.FundActivity
import com.example.composepractice.MusicSplashActivity
import com.example.composepractice.viewmodel.MainMenu
import com.example.composepractice.viewmodel.MusicViewModel

@ExperimentalFoundationApi
@Composable
fun MusicPage(musicViewModel: MusicViewModel = viewModel()){
    val context = LocalContext.current

    Greeting(menuList = musicViewModel.menuList()){
            menu ->
            when(menu){
                is MainMenu.Fund -> {
                    FundActivity.go(context = context)
                }
                is MainMenu.NetEaseMusic -> {
                    MusicSplashActivity.go(context = context)
                }
            }
    }
}

@ExperimentalFoundationApi
@Composable
fun Greeting(menuList:List<MainMenu>,onMenuClick:((MainMenu) -> Unit)? = null){
    LazyVerticalGrid(cells = GridCells.Fixed(count = 3)){
        items(menuList){
            Column(modifier = Modifier
                .fillParentMaxWidth(0.8f)
                .padding(8.dp)
                .clickable {
                    onMenuClick?.invoke(it)
                }) {
                    Image(painter = painterResource(id = it.icon), contentDescription = "")
                    Text(text = it.name)
            }
        }
    }
}