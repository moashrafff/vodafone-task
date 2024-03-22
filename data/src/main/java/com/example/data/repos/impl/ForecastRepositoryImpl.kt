package com.example.data.repos.impl

import com.example.data.api.WeatherService
import com.example.data.local.dao.ForecastModelDao
import com.example.data.model.ForecastModel
import com.example.data.repos.ForeCastRepository
import com.example.data.resource.ResponseResult
import com.example.data.resource.apiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Named

class ForecastRepositoryImpl(
    @Named("io") private val ioDispatcher: CoroutineDispatcher,
    private val forecastDao: ForecastModelDao,
    private val weatherApi: WeatherService
) : ForeCastRepository {

    override suspend fun getForecastDetails(
        lat: Double,
        lon: Double,
        cityId : Int
    ): ResponseResult<ForecastModel> {
        return withContext(ioDispatcher) {

            val localData = forecastDao.getSavedForecastModelByName(cityId.toString())

            if (localData != null) {
                return@withContext ResponseResult.Success(localData)
            }

            val result = apiCall(operation = {
                weatherApi.getForecastDetails(
                    lat, lon
                )
            }, converter = {
                ForecastModel.toForeCastModel(it)
            }, isValidResponse = {
                it != null
            })
            if (result is ResponseResult.Success) forecastDao.insertForecastModel(result.data)
            result
        }
    }

}