package com.example.composepractice.ui.movie

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.composepractice.R
import com.example.composepractice.views.StaggeredVerticalGrid
import com.example.composepractice.data.MovieItemModel
import com.example.composepractice.ext.formatDateMsByMS
import com.example.composepractice.view.LoadingPage
import com.example.composepractice.view.TitleBar
import com.example.composepractice.viewmodel.MovieViewModel

@Composable
fun MoviePage(movieViewModel:MovieViewModel = viewModel()){
    Column(Modifier.fillMaxSize()) {
        TitleBar(text = stringResource(id = R.string.recommend_movie_title))

        val state by movieViewModel.stateLiveData.observeAsState()
        val movieList by movieViewModel.moviesLiveData.observeAsState(listOf())

        LoadingPage(
            state = state!!,
            loadInit = { movieViewModel.getMovieLists() }) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                StaggeredVerticalGrid(maxColumnWidth = 220.dp, modifier = Modifier.padding(4.dp)) {
                    movieList.forEach {
                        MovieItem(it)
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(model: MovieItemModel) {
    val context = LocalContext.current
    Card(
        Modifier
            .padding(4.dp)
    ) {
        Column(
            Modifier
                .clickable {
                    VideoDetailActivity.go(
                        context as Activity,
                        model.data.playUrl,
                        model.data.title
                    )
                }) {
            Box {
                Image(
                    painter = rememberImagePainter(
                        data = model.data.cover.feed,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                )
                Text(
                    formatDateMsByMS((model.data.duration * 1000).toLong()),
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Text(
                text = model.data.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 10.dp, 0.dp, 3.dp)
            )
            Text(
                model.data.category,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 10.dp)
            )
        }
    }
}