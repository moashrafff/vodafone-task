package com.example.data.repos

import com.example.data.resource.Constants
import retrofit2.http.Query

interface ForeCastRepository {
    suspend fun getForecastDetails(
         lat:Double,
         lon:Double
    )
}