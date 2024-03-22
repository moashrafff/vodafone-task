package com.example.forecast.screen

import com.example.data.model.CurrentWeatherModel
import com.example.data.model.ForecastModel

data class ForecastState(
    val isLoading: Boolean,
    val message: String,
    val currentWeather: CurrentWeatherModel?,
    val forecast: ForecastModel?
)
