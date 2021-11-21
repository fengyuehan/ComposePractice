package com.example.composeweather.view

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.composeweather.util.Destinations

class PlayActions(navController: NavHostController) {
    val toWeatherList:() -> Unit = {
        navController.navigate(Destinations.WEATHER_LIST_ROUTE)
    }

    val toCityList:() -> Unit = {
        navController.navigate(Destinations.CITY_LIST_ROUTE)
    }

    val upPress:() -> Unit = {
        navController.navigateUp()
    }
}