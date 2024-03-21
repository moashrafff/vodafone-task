package com.example.vodafone_task.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.ConnectivityObserver
import com.example.core.DeviceSizeType
import com.example.home.screen.HomeScreen
import com.example.search.SearchScreen


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    connectivityState: ConnectivityObserver.Status,
    deviceSizeType: DeviceSizeType
) {

    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AppScreens.Home.action
    ) {
        composable(route = AppScreens.Home.action
        ) {
            HomeScreen(deviceSizeType = deviceSizeType,
                connectivityState = connectivityState,
                onSearch = {
                    navController.navigate(AppScreens.Search.action)
                }, onWeatherDetail = {
//                    navController.navigate(
//                        AppScreens.Forecast.route.replace(
//                            "{name}",
//                            it.location.name
//                        )
//                    )
                })
        }
        composable(route = AppScreens.Search.action) {
            SearchScreen(
                deviceSizeType = deviceSizeType,
                connectivityState = connectivityState, onBack = navController::navigateUp
            )
        }
        composable(route = AppScreens.Details.action,
            arguments = listOf(
                navArgument(
                    name = "name"
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            val name = it.arguments?.getString("name") ?: ""
//            DetailScreen(
//                name = name,
//                deviceSizeType = deviceSizeType,
//                connectivityState = connectivityState,
//                onBack = navController::navigateUp
//            )
        }
    }
}