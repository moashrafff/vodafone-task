package com.example.data.repos

import com.example.data.model.CurrentWeatherModel
import com.example.data.resource.ResponseResult
import retrofit2.http.Query

interface CurrentWeatherRepository {

    suspend fun getWeatherFromDB(name: List<String>): List<CurrentWeatherModel>
    suspend fun getWeatherFromDB(name: String): CurrentWeatherModel?
    suspend fun getCurrentWeather(
        lat:Double,
        lon:Double,
        name: String = "${lat},${lon}",
        shouldSaveLocally: Boolean = true
    ): ResponseResult<CurrentWeatherModel>
}