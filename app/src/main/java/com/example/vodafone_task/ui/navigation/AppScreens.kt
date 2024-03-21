package com.example.vodafone_task.ui.navigation

sealed class AppScreens(val action: String) {
    data object Home: AppScreens("home")
    data object Search: AppScreens("search")
    data object Details: AppScreens("details/{name}")
}