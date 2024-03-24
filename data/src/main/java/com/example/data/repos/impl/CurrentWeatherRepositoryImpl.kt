package com.example.data.repos.impl

import com.example.data.api.WeatherService
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.model.CurrentWeatherModel
import com.example.data.repos.CurrentWeatherRepository
import com.example.data.resource.ResponseResult
import com.example.data.resource.apiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Named

// 30.0444,31.2357
// 30.0443879,31.2357257

class CurrentWeatherRepositoryImpl(
    @Named("io") private val ioDispatcher: CoroutineDispatcher,
    private val weatherDao: CurrentWeatherDao,
    private val weatherApi: WeatherService
) : CurrentWeatherRepository {
    override suspend fun getWeatherFromDB(name: List<String>): List<CurrentWeatherModel>  = weatherDao.getSavedWeatherByNames(name)



    override suspend fun getWeatherFromDB(name: String): CurrentWeatherModel?  = weatherDao.getSavedWeatherByName(name)

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        name: String,
        shouldSaveLocally: Boolean
    ): ResponseResult<CurrentWeatherModel>
        = withContext(ioDispatcher) {
            val localData = weatherDao.getSavedWeatherByName(name)
            if (localData != null) {
                return@withContext ResponseResult.Success(localData)
            }
            val result = apiCall(operation = {
                weatherApi.getCurrentWeather(lat,lon)
            }, converter = {
               CurrentWeatherModel.toCurrentModel(it)
            }, isValidResponse = {
                it != null
            })
            if (result is ResponseResult.Success && shouldSaveLocally) weatherDao.insertWeather(result.data)
            result
        }

}