package com.example.composeweather.view

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*
import com.example.composeweather.R
import com.example.composeweather.common.Loading
import com.example.composeweather.common.PageStateControl
import com.example.composeweather.model.WeatherModel
import com.example.composeweather.room.CityInfo
import com.example.composeweather.util.IconUtils
import com.example.composeweather.util.ImageLoader
import com.qweather.sdk.bean.weather.WeatherNowBean

@Composable
fun WeatherPage(
    weatherViewModel: WeatherViewModel,
    cityInfo: CityInfo,
    onErrorClick: () -> Unit,
    cityList: () -> Unit,
    cityListClick: () -> Unit
){
    val context = LocalContext.current
    val weatherModel by weatherViewModel.weatherModel.observeAsState(initial = Loading)
    val scrollState = rememberScrollState()
    //coerceAtLeast用于判断传入的值是否大于传入默认的最小值，如果大于就直接返回这个值，否则默认返回这个默认的最小值
    //coerceAtLeast 取较大值
    //coerceAtMost 去较小值
    val fontSize = (50f / (scrollState.value / 2) * 70).
    coerceAtLeast(20f).
    coerceAtMost(45f).sp
    val config = LocalConfiguration.current

    PageStateControl(state = weatherModel, onClick = onErrorClick){ weather ->
        Box(modifier = Modifier.fillMaxSize()){
            ImageLoader(data = IconUtils.getWeatherAnimationIcon(context = context, weather =weather.nowBaseBean.icon), modifier = Modifier.fillMaxSize())
            val isLand = config.orientation == Configuration.ORIENTATION_LANDSCAPE
            if (isLand){
                HorizontalWeather(fontSize, cityList, cityListClick, cityInfo, weather, scrollState)
            } else {
                // 竖屏适配
                VerticalWeather(fontSize, cityInfo, cityList, cityListClick, weather, scrollState)
            }
        }
    }
}

@Composable
fun VerticalWeather(fontSize: TextUnit,
                    cityInfo: CityInfo,
                    cityList: () -> Unit,
                    cityListClick: () -> Unit,
                    weather: WeatherModel,
                    scrollState: ScrollState
) {

}

@Composable
fun HeaderWeather(fontSize: TextUnit,
                  cityList: () -> Unit,
                  cityListClick: () -> Unit,
                  cityInfo: CityInfo,
                  nowBaseBean: WeatherNowBean.NowBaseBean?,
                  isLand: Boolean) {
    AnimatedVisibility(
        visible = fontSize.value > 40f || isLand,
        enter = fadeIn() + expandVertically (animationSpec = tween(200)),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderAction(Modifier.fillMaxWidth(), cityListClick, cityList)
            Text(
                text = "${cityInfo.city} ${cityInfo.name}",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 30.sp,
                color = MaterialTheme.colors.primary
            )

            Text(
                text = "${nowBaseBean?.text ?: stringResource(id = R.string.default_weather)}  ${nowBaseBean?.temp ?: "0"}℃",
                modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                fontSize = if (isLand) 45.sp else fontSize,
                color = MaterialTheme.colors.primary
            )
        }

    }
}

@Composable
fun HeaderAction(modifier: Modifier = Modifier, cityListClick: () -> Unit, cityList: () -> Unit) {
    Row(modifier = modifier.wrapContentWidth(Alignment.End)) {
        IconButton(onClick = cityListClick,modifier.wrapContentWidth(Alignment.End)) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "add")
        }
        IconButton(onClick = cityList, modifier = Modifier.wrapContentWidth(Alignment.Start)) {
            Icon(imageVector = Icons.Rounded.List, contentDescription = "list")
        }
    }
}

@Composable
fun HorizontalWeather(fontSize: TextUnit,
                      cityList: () -> Unit,
                      cityListClick: () -> Unit,
                      cityInfo: CityInfo,
                      weather: WeatherModel,
                      scrollState: ScrollState
) {
    Row(modifier = Modifier.fillMaxSize()) {
        val landModifier = Modifier
            .weight(1f)
            .fillMaxHeight()
        Column(modifier = landModifier, horizontalAlignment = Alignment.CenterHorizontally) {
            // 天气头部，向上滑动时会进行隐藏
            HeaderWeather(
                fontSize, cityList, cityListClick, cityInfo, weather.nowBaseBean, true
            )
            WeatherAnimation(weather.nowBaseBean.icon)
        }
        WeatherContent(
            landModifier, scrollState, fontSize, cityList,
            cityListClick, cityInfo, weather, true
        )
    }
}

@Composable
fun WeatherContent(
    landModifier: Modifier = Modifier,
    scrollState: ScrollState,
    fontSize: TextUnit,
    cityList: () -> Unit,
    cityListClick: () -> Unit,
    cityInfo: CityInfo,
    weather: WeatherModel,
    b: Boolean
) {

}

@Composable
fun WeatherAnimation(icon: String?) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            IconUtils.getWeatherAnimationIcon(context, icon)
        )
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        modifier = Modifier.size(130.dp),
        progress = progress
    )
}
