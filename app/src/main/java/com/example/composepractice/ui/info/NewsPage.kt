package com.example.composepractice.ui.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composepractice.data.NewsModelModel
import com.example.composepractice.viewmodel.NewsViewModel

@Composable
fun NewsPage(newsViewModel:NewsViewModel = viewModel()){
    val state by newsViewModel.stateLiveData.observeAsState()
    val newsModelModel by newsViewModel.newsLiveData.observeAsState(initial = NewsModelModel())


}