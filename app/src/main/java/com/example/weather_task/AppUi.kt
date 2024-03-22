package com.example.weather_task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.weather_task.ui.navigation.AppNavigation
import com.example.core.ConnectivityObserver
import com.example.core.DeviceSizeType
import com.example.core.components.NetworkNotAvailableTile


@Composable
fun WeatherApp(
    connectivityObserver: ConnectivityObserver,
    deviceSizeType: DeviceSizeType
) {
    val connectivityState = connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.NetworkAvailable)

    Column {
        AppNavigation(modifier = Modifier.weight(1f), connectivityState = connectivityState.value, deviceSizeType = deviceSizeType)
        AnimatedVisibility(visible = connectivityState.value == ConnectivityObserver.Status.NetworkUnavailable) {
            NetworkNotAvailableTile(modifier = Modifier.animateContentSize())
        }
    }
}