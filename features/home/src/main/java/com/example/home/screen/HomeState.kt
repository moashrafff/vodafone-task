package com.example.home.screen

import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel

data class HomeState(
    val isLoading: Boolean,
    val showGpsDialog: Boolean,
    val message: String,
    val weather: List<WeatherCardState>
) {
    data class WeatherCardState(
        val location: LocationModel,
        val weather: CurrentWeatherModel?
    )
}