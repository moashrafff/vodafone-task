package com.example.weather_task.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ConnectivityObserver
import com.example.core.DeviceSizeType
import com.example.core.ui.WeatherTheme
import com.example.weather_task.WeatherApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = // current theme value

                    WeatherTheme {
                        val deviceType =
                            DeviceSizeType.calculateFromWindowSizeClass(
                                calculateWindowSizeClass(
                                    activity = this
                                )
                            )
                        WeatherApp(
                            connectivityObserver = connectivityObserver,
                            deviceSizeType = deviceType
                        )
                    }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}