package com.example.composeweather.weight

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.composeweather.room.CityInfo
import com.example.composeweather.util.FeatureRequiresLocationPermissions
import com.example.composeweather.util.getLocation
import com.example.composeweather.view.WeatherPage
import com.example.composeweather.view.WeatherViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun WeatherViewPager(
    weatherViewModel: WeatherViewModel,
    toCityList:()->Unit,
    toWeatherList:()->Unit
){
    val cityInfoList by weatherViewModel.cityInfoList.observeAsState(listOf())
    val initialPage by weatherViewModel.searchCityInfo.observeAsState(0)
    val pagerState = rememberPagerState()

    cityInfoList.apply {
        if (isNullOrEmpty()){
            return@apply
        }
        val index = if (pagerState.currentPage > size -1){
            0
        }else{
            pagerState.currentPage
        }
        val cityInfo = get(index)
        val location = getLocation(cityInfo)
        if (location != null){
            LaunchedEffect(location){
                weatherViewModel.getWeather(location)
            }
        }
    }
    WeatherViewPager(
        weatherViewModel, initialPage,
        cityInfoList, pagerState, toCityList, toWeatherList
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalPagerApi
@Composable
fun WeatherViewPager(
    weatherViewModel: WeatherViewModel,
    initialPage: Int = 0,
    cityInfoList: List<CityInfo>,
    pagerState: PagerState,
    toCityList: () -> Unit,
    toWeatherList: () -> Unit,
){
    if (pagerState.currentPage == 0){
        FeatureRequiresLocationPermissions(weatherViewModel = weatherViewModel)
    }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()){
        if (initialPage > 0 && initialPage < pagerState.pageCount){
            coroutineScope.launch {
                pagerState.scrollToPage(initialPage)
                weatherViewModel.onSearchCityInfoChanged(0)
            }
        }
        HorizontalPager(count = cityInfoList.size, state = pagerState) {
            page ->
            WeatherPage(
                weatherViewModel = weatherViewModel,
                cityInfo = cityInfoList[page],
                onErrorClick = {
                    val location = getLocation(cityInfoList[page])
                    if (location != null) {
                        weatherViewModel.getWeather(location)
                    } },
                cityList = toCityList, cityListClick = toWeatherList)
        }
    }
}

fun getLocation(
    cityInfo: CityInfo?
): String? {
    if (cityInfo == null) return null
    return if (cityInfo.locationId.isNotEmpty()) {
        cityInfo.locationId
    } else {
        cityInfo.location
    }
}