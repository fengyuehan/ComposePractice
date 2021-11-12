package com.example.composepractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composepractice.ui.info.NewsPage
import com.example.composepractice.ui.movie.MoviePage
import com.example.composepractice.ui.music.MusicPage
import com.example.composepractice.ui.picture.PicturePage
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.ui.weather.WeatherPage
import com.example.composepractice.viewmodel.MainViewModel
import com.example.composepractice.viewmodel.WeatherViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @ExperimentalFoundationApi
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * 1、setContent方法里 通过创建ComposeView，并当作R.layout.xxx 设置到原setContentView方法中，来初始化内容视图。
         * 2、@Composable 函数最后生成LayoutNode，一些列@Composable 最后构建成了一个以root为根的LayoutNode树
         * 3、Modifier 绑定到 LayoutNode 中，每一个Modifier的扩展方法首先会通过 then 方法生成CombinedModifier的链，
         *   即每一个CombinedModifier 中 都包含上一个扩展方法返回的 Modifier，然后在 LayoutNode 中转换为LayoutNodeWrapper链，
         *   然后测量过程中递归遍历所有LayoutNodeWrapper链中所有LayoutNodeWrapper进行各个属性测量，最后回到LayoutNodeWrapper链中
         *   最后一个节点 InnerPlaceable(LayoutNode成员属性中初始化) 中开始测量当前LayoutNode子node，然后记录测量结果。
         * 4、测量流程 从我们的 AndroidComposeView 的 onMeasure 方法中开始，通过AndroidComposeView中的root来递归测量所有的LayoutNode，
         *   每一个LayoutNode又通过 LayoutNodeWrapper链测量。
         * 5、绘制draw入口 也在 AndroidComposeView 里，在 dispatchDraw 方法里调用了 root.draw(this)
         */
        setContent {
            //observeAsState 该函数的作用就是将ViewModel提供的LiveData数据转换为Compose需要的State数据。
            ComposePracticeTheme {
                Column {
                    val pageState = rememberPagerState(initialPage = 0)
                    HorizontalPager(
                        state = pageState,
                        modifier = Modifier
                            .weight(1f)
                            .scrollable(
                                state = rememberScrollState(),
                                enabled = false,
                                orientation = Orientation.Horizontal
                            ),
                        count = listItem.size
                    ) { page ->
                        when(page){
                            0 -> {
                                NewsPage()
                            }
                            1 -> {
                                MoviePage()
                            }
                            2 -> {
                                PicturePage()
                            }
                            3 -> {
                                MusicPage()
                            }
                            4 -> {
                                WeatherPage()
                            }
                        }

                    }
                    Log.e("zzf","pageState =  " + pageState.currentPage);
                    BottomNavigationAlwaysShowLabelComponent(pagerState = pageState)
                }
            }
        }
    }
}

val listItem = listOf(
    "新闻",
    "视频",
    "美女",
    "音乐",
    "天气"
)

/**
 * Composable的生命周期
 *
 * onActive :当Composable首次进入组件树的时候
 * onCommit :当UI随着recomposition发生更新的时候
 * onDispose : 当Composable从组件树移除的时候
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomNavigationAlwaysShowLabelComponent(pagerState: PagerState){
    /**
     * 作用：用来创建并获取某个跨越函数栈存在的数据
     *  rememberCoroutineScope创建CoroutineScope，CoroutineScope不会随函数的执行反复创建
     *  Scope与Composable生命周期一致，随着onDispose而cancel，避免泄露、
     *  Dispatcher通常为AndroidUiDispatcher.Main
     */
    val scope = rememberCoroutineScope()
    Log.e("zzf","BottomNavigation pagerState  : " + pagerState.currentPage)
    BottomNavigation(backgroundColor = Color.White) {
        listItem.forEachIndexed { index, label ->
            BottomNavigationItem(
                icon = {
                    when(index){
                        0 -> BottomIcon(image = Icons.Filled.Home, selectIndex = pagerState.currentPage, index = index)
                        1 -> BottomIcon(image = Icons.Filled.List, selectIndex = pagerState.currentPage, index = index)
                        2 -> BottomIcon(image = Icons.Filled.Favorite, selectIndex = pagerState.currentPage, index = index)
                        3 -> BottomIcon(image = Icons.Filled.ThumbUp, selectIndex = pagerState.currentPage, index = index)
                        4 -> BottomIcon(image = Icons.Filled.Place, selectIndex = pagerState.currentPage, index = index)
                    }
                },
                label = {
                    Text(
                        text = label,
                        color = if (index == pagerState.currentPage) MaterialTheme.colors.primary else Color.Gray
                    )
                },
                selected = index == pagerState.currentPage ,
                onClick = {
                    //viewModel.saveSelectIndex(pagerState.currentPage)
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                })
        }
    }
}

@Composable
private fun BottomIcon(image:ImageVector,selectIndex:Int,index:Int){
    Icon(
        imageVector = image,
        null,
        tint = if (selectIndex == index) MaterialTheme.colors.primary else Color.Gray
    )
}