package com.example.forecast.usecase

import com.example.data.model.ForecastModel
import com.example.data.resource.ResponseResult
import kotlinx.coroutines.flow.Flow

interface GetForecastUseCase {
    suspend operator fun invoke(lat:Double , lon:Double , cityId: Int): Flow<ResponseResult<ForecastModel>>

}