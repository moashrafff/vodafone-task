package com.example.home.usecase

import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.data.resource.ResponseResult
import kotlinx.coroutines.flow.Flow

interface GetCurrentWeatherInformationUseCase {
    suspend operator fun invoke(locations: List<LocationModel>): Flow<ResponseResult<Map<LocationModel, CurrentWeatherModel?>>>
}