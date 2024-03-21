package com.example.data.api

import retrofit2.http.GET

interface WeatherService {
@GET("")
suspend fun getCurrentWeather(){}
}