package com.example.data.api

import com.example.data.dto.CurrentWeatherDto
import com.example.data.dto.ForecastDto
import com.example.data.dto.LocationDto
import com.example.data.resource.Constants
import com.example.data.resource.EndPoints
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(Constants.BASE_WEATHER_API_URL + EndPoints.CURRENT_LOCATION)
    suspend fun getCurrentWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("units") units : String = Constants.METRIC_UNIT
    ) : CurrentWeatherDto?

    @GET(Constants.BASE_WEATHER_API_URL + EndPoints.SEARCH_LOCATION)
    suspend fun searchLocation(
        @Query("q") q: String,
        @Query("limit") limit: Int? = null,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY
    ): List<LocationDto?>?

    @GET(Constants.BASE_WEATHER_API_URL + EndPoints.FORECAST_DETAILS)
    suspend fun getForecastDetails(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("cnt") cnt:Int = 7,
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("units") units : String = Constants.METRIC_UNIT
    ) : ForecastDto
}