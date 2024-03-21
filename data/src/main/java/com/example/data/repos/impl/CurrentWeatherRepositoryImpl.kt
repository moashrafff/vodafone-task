package com.example.data.repos.impl

import com.example.data.api.WeatherService
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.repos.CurrentWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher

class CurrentWeatherRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherDao: CurrentWeatherDao,
    private val weatherApi: WeatherService
) : CurrentWeatherRepository {

}