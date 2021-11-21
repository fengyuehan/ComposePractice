package com.example.composeweather.util

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

object Destinations{
    const val HOME_PAGE_ROUTE = "home_page_route"
    const val WEATHER_LIST_ROUTE = "weather_list_route"
    const val CITY_LIST_ROUTE = "city_list_route"
}

fun NavGraphBuilder.setComposable(
    route:String,
    arguments:List<NamedNavArgument> = emptyList(),
    deepLinks :List<NavDeepLink> = emptyList(),
    content:@Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
){
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = {
            _,_ -> expandVertically(animationSpec = tween(300))
        },
        exitTransition = {
            _,_ -> shrinkOut(animationSpec = tween(300))
        }
    )
}