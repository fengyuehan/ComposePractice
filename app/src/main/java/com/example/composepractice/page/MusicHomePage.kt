package com.example.composepractice.page

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.composepractice.MusicScreen
import com.example.composepractice.R
import com.example.composepractice.ViewState
import com.example.composepractice.data.*
import com.example.composepractice.ui.theme.LightGray
import com.example.composepractice.utils.simpleNumText
import com.example.composepractice.viewmodel.DiscoveryViewData
import com.example.composepractice.viewmodel.MusicHomeViewModel
import com.example.composepractice.viewmodel.MyPlayListLoaded
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun MusicHomePage(
    user: User?,
    modifier: Modifier,
    musicHomeViewModel: MusicHomeViewModel = hiltViewModel(),
    onScreen :((Any) -> Unit)? = null
){
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        //抽屉的内容
        drawerContent = {Drawer(user)},
        //抽屉打开或关闭的状态
        scaffoldState = scaffoldState,
        modifier = modifier
    ) {
        Home(musicHomeViewModel,onScreen = onScreen,onDrawerClick = {
            coroutineScope.launch {
                scaffoldState.drawerState.open()
            }
        })
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun Home(musicHomeViewModel: MusicHomeViewModel? = null,
         onScreen: ((Any) -> Unit)? = null,
         onDrawerClick: (() -> Unit)? = null) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray)) {
        val discoveryViewData by musicHomeViewModel?.discoveryData?.collectAsState()?:return
        val myPlayListData by musicHomeViewModel?.myPlayList?.collectAsState()?:return
        Row(modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(color = LightGray),verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {onDrawerClick?.invoke()},modifier = Modifier.size(36.dp)) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null,tint = Color.Gray)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "大家都在搜 陈奕迅", color = Color.LightGray)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        val pages: List<Pair<String, @Composable () -> Unit>> = listOf(
            "发现" to { Discovery(discoveryViewData, onToScreen = onScreen) },
            "我的" to { Mine(myPlayListData, onToScreen = onScreen) },
            "动态" to { NewAction() }
        )
        val pagerState = rememberPagerState()
        val pagerScope = rememberCoroutineScope()
        TabRow(selectedTabIndex = pagerState.currentPage,backgroundColor = Color.Transparent) {
            pages.forEachIndexed { index, page ->
                Tabs(tab = page.first, selected = index == pagerState.currentPage) {
                    pagerScope.launch { pagerState.scrollToPage(index) }
                }
            }
        }
        HorizontalPager(count = 3,state = pagerState,verticalAlignment = Alignment.Top) {
                page -> pages[page].second()
        }
    }
}

@Composable
fun Tabs(tab:String,selected:Boolean,onClick:()->Unit){
    Tab(selected = selected, onClick = onClick,modifier = Modifier.height(48.dp)) {
        Text(text = tab)
    }

}

@Composable
fun NewAction() {
    Box(Modifier.fillMaxSize()) {
        Text(text = "动态", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Mine(myPlayList: ViewState, onToScreen: ((Any) -> Unit)? = null) {
    val (loaded, playList) = if (myPlayList !is MyPlayListLoaded) {
        //未载入状态
        false to MyPlayListLoaded(emptyList(), null)
    } else {
        true to myPlayList
    }
    val topMenus = listOf(
        "本地音乐" to R.drawable.icon_music,
        "最近播放" to R.drawable.icon_late_play,
        "下载管理" to R.drawable.icon_download_black,
        "我的电台" to R.drawable.icon_broadcast,
        "我的收藏" to R.drawable.icon_collect,
    )
    var selfCreatesExpand by remember { mutableStateOf(true) }
    var collectsExpand by remember { mutableStateOf(false) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(topMenus.size) { index ->
            val menu = topMenus[index]
            Row(Modifier.height(56.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = menu.second),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(10.dp)
                        .placeholder(!loaded)
                )
                Text(text = menu.first, modifier = Modifier.weight(5.0f))
            }
            Divider()
        }
        item {
            PlaylistTitle(title = "创建的歌单", count = playList.selfCreates.size, selfCreatesExpand) {
                selfCreatesExpand = !selfCreatesExpand
            }
        }
        if (selfCreatesExpand) {
            items(playList.selfCreates) {
                PlaylistItem(playList = it) { onToScreen?.toPlayListScreen(it) }
            }
        }
        item {
            PlaylistTitle(title = "收藏的歌单", count = playList.collects.size, collectsExpand) {
                collectsExpand = !collectsExpand
            }
        }
        if (collectsExpand) {
            items(playList.collects) {
                PlaylistItem(playList = it) { onToScreen?.toPlayListScreen(it) }
            }
        }
    }
}

/**
 * 跳转到歌单详情页
 */
private fun ((Any) -> Unit).toPlayListScreen(playList: PlayList) {
    this(
        MusicScreen.PlayList(
            Recommend(
                id = playList.id,
                name = playList.name,
                playcount = playList.playCount,
                picUrl = playList.coverImgUrl
            )
        )
    )
}

/**
 * 歌单项
 */
@Composable
private fun PlaylistItem(playList: PlayList, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = rememberImagePainter(data = playList.coverImgUrl.limitSize(80)), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = playList.name, maxLines = 1)
            Text(text = "${playList.trackCount}首", style = MaterialTheme.typography.subtitle2)
        }
    }
}

/**
 * 歌单标题
 */
@Composable
private fun PlaylistTitle(title: String, count: Int, expanded: Boolean, onToggleExpand: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .height(48.dp)
            .clickable { onToggleExpand() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = if (expanded) R.drawable.icon_up else R.drawable.icon_down),
            contentDescription = null,
            Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$title  (${count})")
        Spacer(modifier = Modifier.weight(1.0f))
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(20.dp))
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun Discovery(discoveryViewData: DiscoveryViewData, onToScreen: ((Any) -> Unit)? = null) {
    Column {
        CustomBanner(discoveryViewData.bannerList.map { it.pic.orEmpty() },height = 140){

        }
        CategoryList{ menuName ->
            when(menuName){
                "每日推荐" -> {
                    onToScreen?.invoke(HomeScreen.DailySong)
                }
                "排行榜" ->{
                    onToScreen?.invoke(HomeScreen.TopList)
                }
            }
        }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "推荐歌单",modifier = Modifier.padding(10.dp))
            Box(modifier = Modifier.height(140.dp)) {
                RecommendPlayList(discoveryViewData.recommendList){
                    onToScreen?.invoke(MusicScreen.PlayList(it))
                }
                discoveryViewData.newAlbumList.takeIf { it.isNotEmpty() }?.let {
                    Text(text = "新碟上架", modifier = Modifier.padding(10.dp))
                    Box(modifier = Modifier.height(140.dp)) { NewAlbumList(it) }
                }
                discoveryViewData.topMVList.takeIf { it.isNotEmpty() }?.let {
                    Text(text = "MV 排行", modifier = Modifier.padding(10.dp))
                    Box(modifier = Modifier.height(140.dp)) { TopMvList(it) }
                }
            }

        }
    }
}

@Composable
fun TopMvList(mvList: List<MVData.MV>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(mvList) {
            PlayListWidget(
                text = it.name.orEmpty(), picUrl = it.cover.orEmpty(),
                subText = it.artistName.orEmpty(), maxLines = 2
            ) {

            }
        }
    }
}

@Composable
fun NewAlbumList(albumList: List<Album>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(albumList) {
            PlayListWidget(
                text = it.name.orEmpty(), picUrl = it.picUrl, subText = it.artist?.name.orEmpty(),
                maxLines = 2
            ) {

            }
        }
    }
}

@Composable
fun RecommendPlayList(recommendList:List<Recommend>,onClick:(Recommend) -> Unit){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .placeholder(
                recommendList.isEmpty(),
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.Gray)
            )
    ){
        items(recommendList){
            PlayListWidget(text = it.name,picUrl = it.picUrl,playCount = it.playcount,maxLines = 2){
                onClick(it)
            }
        }
    }
}

@Composable
fun PlayListWidget(text: String, picUrl: String? = null, subText: String? = null, playCount: Long? = null,
                   maxLines: Int? = null, index: Int? = null, onTap: (() -> Unit)?){
    Column(modifier = Modifier
        .width(112.dp)
        .wrapContentHeight()
        .clickable(onTap != null, onClick = { onTap?.invoke() })) {
        val line = maxLines ?: Int.MAX_VALUE
        val overFlow = if (maxLines != null)TextOverflow.Ellipsis else TextOverflow.Clip
        picUrl?.run {
            PlayListCover(playCount = playCount,url = this)
        }
        index?.run {
            Text(text = toString())
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = text, maxLines = line, overflow = overFlow, fontSize = 12.sp)
        subText?.run {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = this,
                style = MaterialTheme.typography.caption,
                maxLines = line,
                overflow = overFlow
            )
        }
    }

}

@Composable
fun PlayListCover(playCount: Long? = null, width: Float = 108f, height: Float = width, radius: Float = 24f,
                  url: String? = null){
    Box(modifier = Modifier.clip(RoundedCornerShape(radius))) {
        Image(
            painter = rememberImagePainter("${url?.limitSize(width.toInt(), height.toInt())}"),
            contentDescription = null,
            modifier = Modifier.size(width.dp, height.dp)
        )
        playCount?.let {
            Row(Modifier.padding(top = 1.dp, end = 3.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.icon_triangle),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp, 20.dp)
                )
                Text(
                    text = it.simpleNumText(),
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * 如果没有参数直接用object修饰，并继承sealed class
 * 如果有参数，则用class修饰，并继承sealed class
 */
internal sealed class HomeScreen(open val route: String) {
    object Home : HomeScreen("home")
    object DailySong : HomeScreen("dailySong")
    object TopList : HomeScreen("topList")
}

@ExperimentalFoundationApi
@Composable
fun CategoryList(onClick: (String) -> Unit){
    val menus = mapOf(
        "每日推荐" to R.drawable.icon_daily,
        "歌单" to R.drawable.icon_playlist,
        "排行榜" to R.drawable.icon_rank,
        "电台" to R.drawable.icon_radio,
        "直播" to R.drawable.icon_look,
    )
    LazyVerticalGrid(modifier = Modifier.wrapContentHeight(),cells = GridCells.Fixed(5)) {
        items(menus.entries.toList().size) {
            index ->
            Column(
                Modifier
                    .aspectRatio(1 / 1.3f)
                    .padding(8.dp)
                    .clickable {
                        onClick(
                            menus.entries
                                .toList()
                                .get(index = index).key
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.radialGradient(
                                listOf(Color(0xFFFF8174), Color.Red),
                                radius = 1.0f
                            ),
                            shape = RoundedCornerShape(50)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(50)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = menus.entries.toList().get(index = index).value), contentDescription = null)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = menus.entries.toList().get(index = index).key, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CustomBanner(urls:List<String>,height:Int,onTap:(Int)->Unit){
    val pageState = rememberPagerState()
    Box(modifier = Modifier
        .height(height = height.dp)
        .fillMaxWidth()
        .padding(8.dp)
        .placeholder(
            urls.isEmpty(),
            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.LightGray)
        )) {
        HorizontalPager(count = urls.size,state = pageState) { page ->
            Image(painter = rememberImagePainter(urls),
                contentDescription = null ,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTap(page) })
        }
        HorizontalPagerIndicator(
            pagerState = pageState, modifier = Modifier
                .padding(8.dp)
                .align(
                    Alignment.BottomCenter
                ),
            activeColor = Color.White,
            inactiveColor = Color.LightGray
        )
    }
}

@Composable
fun Drawer(user: User?) {
    Column(modifier = Modifier
        .background(LightGray)
        .padding(16.dp)
        .fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircleAvatar(url = user?.profile?.avatarUrl)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${user?.profile?.nickname.orEmpty()} >" )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)) {
            DrawerItem("设置",Icons.Outlined.Settings)
            Divider()
            DrawerItem(title = "夜间模式", Icons.Outlined.Check)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
        ) {
            DrawerItem(title = "我的订单", Icons.Outlined.MoreVert)
            Divider()
            DrawerItem(title = "关于", Icons.Outlined.Info)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(16.dp)) {
            Text(text = "退出登录/关闭",color = Color.Red)
        }
    }
}

@Composable
fun DrawerItem(title:String,imageVector: ImageVector? = null){
    Row(modifier = Modifier.padding(16.dp)) {
        imageVector?.let {
            Icon(imageVector = imageVector, contentDescription = null)
        }
        Text(text = title,modifier = Modifier.padding(start = 16.dp))
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null,tint = Color.LightGray)
    }
}

@Composable
fun CircleAvatar(url:String?,size: Dp = 36.dp){
    val sizePx = with(LocalDensity.current){size.roundToPx()}
    Image(painter = rememberImagePainter(url?.limitSize(sizePx),builder = {
        transformations(CircleCropTransformation())
    }), contentDescription = null,modifier = Modifier.size(size = size))
}
