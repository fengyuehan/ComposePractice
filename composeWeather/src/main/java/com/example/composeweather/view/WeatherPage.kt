package com.example.composeweather.view

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
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
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.composeweather.util.getSunriseSunsetContent
import com.example.composeweather.util.getUvIndexDesc
import com.qweather.sdk.bean.weather.WeatherDailyBean

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
// 正常状态下是隐藏状态，向上滑动时展示
    ShrinkHeaderHeather(
        fontSize, cityInfo, cityList, cityListClick, weather.nowBaseBean
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(if (fontSize.value > 40f) 0.dp else 110.dp))
        WeatherContent(
            Modifier, scrollState, fontSize, cityList, cityListClick, cityInfo, weather
        )
    }
}

@Composable
fun ShrinkHeaderHeather(fontSize: TextUnit, cityInfo: CityInfo, cityList: () -> Unit, cityListClick: () -> Unit, weatherNow: WeatherNowBean.NowBaseBean) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedVisibility(
            visible = fontSize.value < 40f,
            enter = fadeIn() + expandVertically(animationSpec = tween(300)),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Row(modifier = Modifier.wrapContentWidth(Alignment.Start)) {
                        IconButton(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start), onClick = {}
                        ) {}
                        IconButton(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.Start), onClick = {}
                        ) {}
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        text = "${cityInfo.city} ${cityInfo.name}",
                        maxLines = 1,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primary,
                        overflow = TextOverflow.Ellipsis,
                    )
                    HeaderAction(cityListClick = cityListClick, cityList = cityList)
                }

                Text(
                    text = "${weatherNow?.text}  ${weatherNow?.temp}℃",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
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
    weather: WeatherModel?,
    isLand: Boolean = false
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.size(30.dp))
        if (!isLand){
            HeaderWeather(
                fontSize = fontSize,
                cityList = { cityList },
                cityListClick = { cityListClick },
                cityInfo = cityInfo,
                nowBaseBean = weather?.nowBaseBean,
                isLand = isLand
            )
            WeatherAnimation(icon = weather?.nowBaseBean?.icon)

            Spacer(modifier = Modifier.height(10.dp))
        }
        // 当前空气质量
        AirQuality(weather?.airNowBean)

        Spacer(modifier = Modifier.height(10.dp))
        // 未来24小时天气预报
        HourWeather(weather?.hourlyBeanList)
        Spacer(modifier = Modifier.height(10.dp))

        // 未来7天天气预报
        DayWeather(weather?.dailyBeanList)

        // 当天具体天气数值
        DayWeatherContent(weather)
    }
}

@Composable
fun DayWeatherContent(weatherModel: WeatherModel?) {
    val context = LocalContext.current
    val dailyBean = weatherModel?.dailyBean
    val nowBaseBean = weatherModel?.nowBaseBean
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp, end = 5.dp)
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.uv_index_title),
            dailyBean?.uvIndex ?: "0",
            getUvIndexDesc(context, dailyBean?.uvIndex)
        )
        WeatherContentItem(
            modifier.fillMaxHeight(),
            stringResource(id = R.string.sun_title),
            getSunriseSunsetContent(
                context,
                dailyBean?.sunrise ?: "07:00",
                dailyBean?.sunset ?: "19:00"
            ),
            "${stringResource(id = R.string.sun_sunrise)}${dailyBean?.sunrise ?: "07:00"}\n${
                stringResource(
                    id = R.string.sun_sunset
                )
            }${dailyBean?.sunset ?: "19:00"}"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.body_temperature_title),
            "${nowBaseBean?.feelsLike ?: "0"}℃",
            stringResource(id = R.string.body_temperature_tip)
        )
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.rainfall_title),
            "${nowBaseBean?.precip ?: "0"}${stringResource(id = R.string.rainfall_unit)}",
            if (nowBaseBean?.precip?.toFloat() ?: 0f > 0f)
                stringResource(id = R.string.rainfall_tip1)
            else stringResource(id = R.string.rainfall_tip2)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.humidity_title),
            "${nowBaseBean?.humidity ?: "0"}%",
            stringResource(id = R.string.humidity_tip)
        )
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.wind_title),
            "${nowBaseBean?.windDir ?: "0"}${nowBaseBean?.windScale ?: ""}${stringResource(id = R.string.wind_unit)}",
            "${stringResource(id = R.string.wind_tip)}${nowBaseBean?.windSpeed ?: "0"}Km/H"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ) {
        val modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.air_pressure_title),
            "${nowBaseBean?.pressure ?: "0"}${stringResource(id = R.string.air_pressure_unit)}",
            stringResource(id = R.string.air_pressure_tip)
        )
        WeatherContentItem(
            modifier,
            stringResource(id = R.string.visibility_title),
            "${nowBaseBean?.vis ?: "0"}${stringResource(id = R.string.visibility_unit)}",
            stringResource(id = R.string.visibility_tip)
        )
    }
}

@Composable
private fun WeatherContentItem(modifier: Modifier, title: String, value: String, tip: String) {
    Card(modifier = modifier, shape = RoundedCornerShape(10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = title, fontSize = 14.sp)
            Text(
                text = value,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 30.sp,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = tip,
                modifier = Modifier.padding(top = 15.dp),
                fontSize = 14.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
}


@Composable
fun DayWeather(list: Iterable<WeatherDailyBean.DailyBean>?) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(10.dp)) {
        Text(
            text = stringResource(id = R.string.seven_day_title),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 7.dp, start = 10.dp, end = 10.dp)
        )
        list?.forEach{
            dailyBean ->
            Divider(modifier = Modifier.padding(horizontal = 10.dp))
            DayWeatherItem(dailyBean)
        }
    }
}

@Composable
fun DayWeatherItem(dailyBean: WeatherDailyBean.DailyBean) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = dailyBean.fxDate,
            modifier = Modifier
                .weight(1f)
                .padding(start = 3.dp),
            fontSize = 15.sp,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        ImageLoader(
            data = IconUtils.getWeatherIcon(context, dailyBean.iconDay),
            modifier = Modifier
                .padding(start = 7.dp)
        )
        Spacer(modifier = Modifier.weight(1.2f))
        Text(
            text = "${dailyBean.tempMin ?: "0"}℃",
            modifier = Modifier
                .padding(start = 7.dp)
                .weight(2f),
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
        Text(
            text = "${dailyBean.tempMax ?: "0"}℃",
            modifier = Modifier
                .padding(start = 7.dp, end = 3.dp)
                .weight(2f),
            fontSize = 15.sp,
            textAlign = TextAlign.End,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun HourWeather(list: List<WeatherHourlyBean.HourlyBean>?) {
    if (list.isNullOrEmpty()){
        return
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp), shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.twenty_four_hour_title),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 7.dp, start = 10.dp, end = 10.dp)
            )
            Divider(modifier = Modifier.padding(horizontal = 10.dp))
            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)){
                items(list){ hourlyBean ->
                    HourWeatherItem(hourlyBean)

                }
            }
        }
    }
}

@Composable
fun HourWeatherItem(hourlyBean: WeatherHourlyBean.HourlyBean) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourlyBean.fxTime,
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary
        )
        ImageLoader(
            data = IconUtils.getWeatherIcon(context, hourlyBean.icon),
            modifier = Modifier.padding(top = 7.dp)
        )
        Text(
            text = "${hourlyBean.temp}℃",
            modifier = Modifier.padding(top = 7.dp),
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun AirQuality(airNowBean: AirNowBean.NowBean?) {
    if (airNowBean == null){
        return
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp), shape = RoundedCornerShape(10.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = stringResource(id = R.string.air_quality_title), fontSize = 14.sp)
            Text(
                text = "${airNowBean.aqi ?: "10"} - ${
                    airNowBean.category ?: stringResource(id = R.string.air_quality_level)
                }",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 24.sp,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "${
                    stringResource(id = R.string.air_quality_Current_aqi)
                }${airNowBean.aqi ?: "10"}${airNowBean.primary ?: ""}",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            AirQualityProgress((airNowBean.aqi ?: "10").toInt())
        }
    }
}

@Composable
fun AirQualityProgress(aqi: Int) {
    val aqiValue = if (aqi < 500){
        aqi
    }else{
        500
    }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp) ){
        drawLine(brush = Brush.linearGradient(
            0.0f to Color(red = 139, green = 195, blue = 74),
            0.1f to Color(red = 255, green = 239, blue = 59),
            0.2f to Color(red = 255, green = 152, blue = 0),
            0.3f to Color(red = 244, green = 67, blue = 54),
            0.4f to Color(red = 156, green = 39, blue = 176),
            1.0f to Color(red = 143, green = 0, blue = 0)),
            start = Offset.Zero,
            end = Offset(size.width,0f),
            strokeWidth = 20f,
            cap = StrokeCap.Round
        )
        drawPoints(
            points = arrayListOf(Offset(size.width / 500 * aqiValue,0f)),
            pointMode = PointMode.Points,
            color = Color.White,
            strokeWidth = 20f,
            cap = StrokeCap.Round
        )
    }
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
