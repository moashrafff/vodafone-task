package com.example.forecast.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repos.CurrentWeatherRepository
import com.example.data.resource.ResponseResult
import com.example.forecast.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailsViewModel @Inject constructor(
    @Named("io") private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: CurrentWeatherRepository,
    private val getForecastDetailsUseCase: GetForecastUseCase
) : ViewModel() {

    var name = ""
        private set

    var cityId = -1
        private set

    var uiState = MutableStateFlow(
        ForecastState(
            isLoading = false,
            message = "Loading...",
            forecast = null,
            currentWeather = null
        )
    )
        private set


    fun loadData(name: String) {
        this.name = name
        fetchForecastData()
    }

    fun separateLatLongFromString(input: String): Pair<Double, Double>? {
        val parts = input.split(",") // Split the string using comma as delimiter
        if (parts.size == 2) {
            try {
                val lat = parts[0].trim().toDouble() // Extract latitude
                val lon = parts[1].trim().toDouble() // Extract longitude
                return Pair(lat, lon)
            } catch (e: NumberFormatException) {
                // Handle parsing errors
                return null
            }
        }
        return null // Invalid format
    }

    fun fetchForecastData() {
        if (uiState.value.isLoading) return
        viewModelScope.launch(ioDispatcher) {

            uiState.emit(
                ForecastState(
                    isLoading = true,
                    message = "Loading...",
                    forecast = uiState.value.forecast,
                    currentWeather = uiState.value.currentWeather
                )
            )
            val lat = separateLatLongFromString(name)?.first ?: 0.0
            val lon = separateLatLongFromString(name)?.second ?: 0.0

            val currentWeather = weatherRepository.getWeatherFromDB("${lat},${lon}")

            getForecastDetailsUseCase.invoke(lat,lon,cityId).map {
                ForecastState(
                    isLoading = it is ResponseResult.Loading || it is ResponseResult.LocalData,
                    message = if (it is ResponseResult.Error) it.error.errorMessage else "",
                    currentWeather = currentWeather,
                    forecast = when (it) {
                        is ResponseResult.LocalData -> {
                            it.data
                        }

                        is ResponseResult.Success -> {
                            it.data
                        }

                        else -> uiState.value.forecast
                    }
                )
            }.collectLatest {
                uiState.emit(it)
            }
        }
    }

}