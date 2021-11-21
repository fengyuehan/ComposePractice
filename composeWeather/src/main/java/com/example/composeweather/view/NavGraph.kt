package com.example.composeweather.view

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composeweather.util.Destinations
import com.example.composeweather.util.setComposable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("RememberReturnType")
@Composable
fun NavGraph(startDestination: String = Destinations.HOME_PAGE_ROUTE){
    val navController = rememberAnimatedNavController()
    val actions = remember(navController){
        PlayActions(navController = navController)
    }

    AnimatedNavHost(navController = navController, startDestination = startDestination){
        setComposable(Destinations.HOME_PAGE_ROUTE){
            val weatherViewModel = hiltViewModel<WeatherViewModel>()
            LaunchedEffect(Unit){
                weatherViewModel.refreshCityList()
            }

        }
    }
}