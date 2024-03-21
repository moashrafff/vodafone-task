package com.example.data.api

import com.example.data.dto.LocationDto
import com.example.data.resource.Constants
import com.example.data.resource.EndPoints
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("")
    suspend fun getCurrentWeather()

    @GET(Constants.BASE_WEATHER_API_URL + EndPoints.SEARCH_LOCATION)
    suspend fun searchLocation(
        @Query("q") q: String,
        @Query("limit") limit: Int? = null,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY
    ): List<LocationDto?>?
}