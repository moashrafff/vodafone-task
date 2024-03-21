package com.example.home.usecase

import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.data.resource.ResponseResult

interface GetCurrentWeatherLocationByUserLocationUseCase {
    suspend operator fun invoke(location: LocationModel): ResponseResult<CurrentWeatherModel>
}