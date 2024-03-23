package com.example.home.usecase.impl

import android.location.Location
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.data.repos.CurrentWeatherRepository
import com.example.data.resource.ResponseResult
import com.example.home.usecase.GetCurrentWeatherLocationByUserLocationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCurrentWeatherLocationByUserLocationUseCaseImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: CurrentWeatherRepository
): GetCurrentWeatherLocationByUserLocationUseCase {

    override suspend fun invoke(location: Location): ResponseResult<CurrentWeatherModel> {
        return withContext(ioDispatcher) {
            weatherRepository.getCurrentWeather(lat = location.latitude , lon = location.longitude, shouldSaveLocally = false)
        }
    }

}