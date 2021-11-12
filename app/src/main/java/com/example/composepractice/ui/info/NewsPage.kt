package com.example.composepractice.ui.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
import coil.transform.RoundedCornersTransformation
import com.example.composepractice.R
import com.example.composepractice.data.NewsModelModel
import com.example.composepractice.data.StoryModel
import com.example.composepractice.data.TopStoryModel
import com.example.composepractice.ext.toPx
import com.example.composepractice.views.LoadingPage
import com.example.composepractice.views.TitleBar
import com.example.composepractice.viewmodel.NewsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@Composable
fun NewsPage(newsViewModel:NewsViewModel = viewModel()){
    val state by newsViewModel.stateLiveData.observeAsState()
    val modelModel by newsViewModel.newsLiveData.observeAsState(initial = NewsModelModel())

    LoadingPage(
        state = state!!,
        loadInit = {
            newsViewModel.getNewsList()
        },
        contentView = {
            Column(modifier = Modifier.fillMaxSize()) {
                TitleBar(text = stringResource(id = R.string.information_title))
                LazyColumn{
                    val stories = modelModel.stories
                    itemsIndexed(stories){
                        index, item ->
                        if (index == 0){
                            NewsBanner(modelModel.top_stories)
                        }else{
                            NewsItem(item)
                            if (index != stories.size - 1){
                                Divider(
                                    thickness = 0.5.dp,
                                    modifier = Modifier.padding(8.dp, 0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun NewsItem(model: StoryModel) {
    val context = LocalContext.current
    Row(
        Modifier
            .padding(10.dp)
            .clickable {
                NewsDetailActivity.go(context, model.title, model.url)
            }) {
        Image(
            painter = rememberImagePainter(data = model.images[0], builder = {
                crossfade(true)
                transformations(RoundedCornersTransformation(2.toPx().toFloat()))
            }),
            null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(120.dp)
                .height(80.dp)
        )

        Column(Modifier.weight(1f)) {
            Text(
                model.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 3.dp, 0.dp, 0.dp)
            )
            Text(
                model.hint,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(10.dp, 5.dp, 0.dp, 0.dp)
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun NewsBanner(topStories:List<TopStoryModel>){
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)){
            val pagerState = rememberPagerState(initialPage = 0)
            HorizontalPager(count = topStories.size,state = pagerState,modifier = Modifier.fillMaxSize()) {
                page ->
                Image(rememberImagePainter(data = topStories[page].image,
                            builder = {
                                crossfade(true)
                            }),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    NewsDetailActivity.go(context, topStories[page].title, topStories[page].url)
                                }
                )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.BottomCenter),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = Color.White
        )
    }

}