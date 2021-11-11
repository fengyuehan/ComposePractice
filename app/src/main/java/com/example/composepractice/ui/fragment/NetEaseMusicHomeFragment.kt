package com.example.composepractice.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import coil.compose.rememberImagePainter
import com.example.composepractice.MusicScreen
import com.example.composepractice.R
import com.example.composepractice.page.MusicHomePage
import com.example.composepractice.ui.theme.Blue500
import com.example.composepractice.ui.theme.ComposePracticeTheme
import com.example.composepractice.viewmodel.MusicHomeState
import com.example.composepractice.viewmodel.MusicHomeViewModel
import com.example.composepractice.viewmodel.PlaySongsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetEaseMusicHomeFragment: Fragment() {
    private val viewModel by viewModels<MusicHomeViewModel>()
    private val playSongViewModel by activityViewModels<PlaySongsViewModel>()
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeContent{
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = Blue500
            )
        }
        val userState by viewModel.userState.collectAsState()
        val playList by playSongViewModel.allSongs.collectAsState()
        Crossfade(targetState = userState) {
            when(it){
                is MusicHomeState.Splash -> {
                    MusicSplash()
                }
                is MusicHomeState.Visitor -> {
                    MusicLogin(viewModel) { requireActivity().finish() }
                }
                is MusicHomeState.Login -> Column {
                    MusicHomePage(it.user, modifier = Modifier.weight(1f)) { screen ->
                        when (screen) {
                            is MusicScreen -> findNavController().navigate(screen.directions)
                        }
                    }
                    if (playList.isNotEmpty()) {
                        //有播放歌单时显示播放控件
                        PlayWidget(playSongViewModel, height = 56.dp) {
                            findNavController().navigate(MusicScreen.PlaySong().directions)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 页面下面的播放条
 */
@Composable
fun PlayWidget(viewModel: PlaySongsViewModel = viewModel(), height: Dp = 72.dp, onClick: () -> Unit) {
    val allSongs by viewModel.allSongs.collectAsState()
    val curSong by viewModel.curSong.collectAsState()
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(color = Color.White)
            .border(1.dp, color = Color.LightGray)
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        if (allSongs.isEmpty()) {
            Text(text = "暂无正在播放的歌曲", modifier = Modifier.align(Alignment.Center))
        } else {
            val curProgress by viewModel.curProgress.collectAsState()
            val isPlaying by viewModel.isPlaying.collectAsState()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(curSong?.picUrl.orEmpty()),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = curSong?.name.orEmpty(), maxLines = 1)
                    Text(text = curSong?.artists.orEmpty())
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Box(modifier = Modifier.size(42.dp)) {
                    Icon(
                        painter = painterResource(if (isPlaying) R.drawable.icon_song_pause else R.drawable.icon_song_play),
                        contentDescription = null,
                        Modifier
                            .fillMaxSize()
                            .clickable {
                                viewModel.togglePlay()
                            }
                    )
                    CircularProgressIndicator(
                        progress = curProgress,
                        modifier = Modifier.padding(5.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}




@Composable
fun MusicLogin(vm: MusicHomeViewModel? = null, onQuit: (() -> Unit)? = null) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "手机号登录") })
    }) {
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "欢迎使用云音乐", style = TextStyle(fontSize = 34.sp))
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = phone,
                label = { Text(text = "手机", style = MaterialTheme.typography.body1) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { phone = it },
                modifier = Modifier.onHandleBack(onQuit))
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(value = password,
                label = { Text(text = "密码", style = MaterialTheme.typography.body1) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it },
                modifier = Modifier.onHandleBack(onQuit))
            Spacer(modifier = Modifier.height(50.dp))
            Button(onClick = { vm?.login(phone, password) }, modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "提交")
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onHandleBack(onBackPressed: (() -> Unit)?): Modifier {
    return onKeyEvent {
        if (it.key == Key.Back && it.type == KeyEventType.KeyUp) {
            onBackPressed?.invoke()
        }
        true
    }
}


@Composable
fun MusicSplash() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.music), contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(128.dp)
        )
    }
}


internal fun Fragment.composeContent(content:@Composable ()-> Unit):View =
    ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        )
        setContent (content = {
            ComposePracticeTheme (content = content)
        })
    }