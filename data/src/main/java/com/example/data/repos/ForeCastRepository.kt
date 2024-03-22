package com.example.data.repos

import com.example.data.model.ForecastModel
import com.example.data.resource.Constants
import com.example.data.resource.ResponseResult
import retrofit2.http.Query

interface ForeCastRepository {
    suspend fun getForecastDetails(
         lat:Double,
         lon:Double,
         cityId : Int
    ) : ResponseResult<ForecastModel>
}