package com.example.home.usecase

import android.location.Location
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.data.resource.ResponseResult

interface GetCurrentWeatherLocationByUserLocationUseCase {
    suspend operator fun invoke(location: Location): ResponseResult<CurrentWeatherModel>
}