package com.example.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.LocationErrorCode
import com.example.data.model.LocationModel
import com.example.data.repos.LocationRepository
import com.example.data.resource.ResponseResult
import com.example.home.usecase.GetCurrentWeatherInformationUseCase
import com.example.home.usecase.GetCurrentWeatherLocationByUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import android.location.Location as AndroidLocation

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val getCurrentWeatherInformationUseCase: GetCurrentWeatherInformationUseCase,
    private val getCurrentWeatherLocationByUserLocationUseCase: GetCurrentWeatherLocationByUserLocationUseCase,
    @Named("io") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var userCurrentLocationInfo: LocationModel? = null
    private var userLocation: AndroidLocation? = null
    private var savedLocations: List<LocationModel> = emptyList()
    private var userDisabledGps = false

    var uiState = MutableStateFlow(
        HomeState(
            isLoading = false,
            message = "Loading...",
            showGpsDialog = false,
            weather = emptyList()
        )
    )
        private set


    fun init() {
        Log.e("TAG102", "loadData: " + userLocation )
        loadData()
    }


    private fun loadData() {

        if (uiState.value.isLoading) return
        viewModelScope.launch(ioDispatcher) {
            uiState.emit(
                HomeState(
                    isLoading = true,
                    message = if (uiState.value.weather.isEmpty()) "Loading..." else "",
                    showGpsDialog = uiState.value.showGpsDialog,
                    weather = uiState.value.weather.distinctBy { it.location.name }
                        .sortedBy { it.location.name }
                )
            )

            if (userLocation != null) {

                val result = getCurrentWeatherLocationByUserLocationUseCase.invoke(userLocation!!)
                Log.e("TAG102", "loadData: " + result )

                if (result is ResponseResult.Success) {

                    val location = result.data.coord
                    userCurrentLocationInfo = LocationModel(
                        name = result.data.name + " , " + result.data.sys.country ,
                         country = "",
                     lat = location.lat,
                     lon = location.lon,
                     state = "")

                    val newList = listOf(
                        HomeState.WeatherCardState(
                            location = userCurrentLocationInfo!!.copy().apply { isUserCurrentLocation = true },
                            weather = result.data
                        )
                    ) + uiState.value.weather



                    uiState.emit(
                        HomeState(
                            isLoading = true,
                            message = "",
                            showGpsDialog = uiState.value.showGpsDialog,
                            weather = newList.distinctBy { it.location.name }
                                .sortedBy { it.location.name }
                        )
                    )
                }
            }
            savedLocations = locationRepository.getLocalLocations().toMutableList().apply {
                if (userCurrentLocationInfo != null) {
                    removeIf { loc ->
                        userCurrentLocationInfo!!.name == loc.name
                    }
                }
            }
            getCurrentWeatherInformationUseCase.invoke(savedLocations).map { res ->
                HomeState(
                    isLoading = res is ResponseResult.Loading || res is ResponseResult.LocalData,
                    message = if (res is ResponseResult.Error) res.error.errorMessage else "",
                    showGpsDialog = uiState.value.showGpsDialog,
                    weather = when (res) {
                        is ResponseResult.LocalData -> (res.data.map { w ->
                            HomeState.WeatherCardState(
                                location = w.key,
                                weather = w.value
                            )
                        } + uiState.value.weather).distinctBy { it.location.name }
                            .sortedBy { it.location.name }

                        is ResponseResult.Success -> (res.data.map { w ->
                            HomeState.WeatherCardState(
                                location = w.key,
                                weather = w.value
                            )
                        } + uiState.value.weather).distinctBy { it.location.name }
                            .sortedBy { it.location.name }

                        else -> uiState.value.weather
                    }
                )
            }.collectLatest {
                uiState.emit(it)
            }
        }
    }


    fun onUserLocationUpdated(location: AndroidLocation) {
        userLocation = location
        init()
    }

    fun onUserLocationError(error: LocationErrorCode) {
        viewModelScope.launch(ioDispatcher) {
            if (error == LocationErrorCode.GPS_DISABLED) {
                uiState.emit(
                    uiState.value.copy(
                        showGpsDialog = !userDisabledGps
                    )
                )
                if (userDisabledGps) init()
            } else {
                init()
            }
        }
    }

    fun onOpenGps() {
        viewModelScope.launch(ioDispatcher) {
            uiState.emit(
                uiState.value.copy(
                    showGpsDialog = false
                )
            )
        }
    }

    fun onUserDeniedGps() {
        userDisabledGps = true
        viewModelScope.launch(ioDispatcher) {
            uiState.emit(
                uiState.value.copy(
                    showGpsDialog = false
                )
            )
            init()
        }
    }

}