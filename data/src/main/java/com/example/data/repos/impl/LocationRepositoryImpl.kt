package com.example.data.repos.impl

import com.example.data.api.WeatherService
import com.example.data.local.dao.LocationDao
import com.example.data.model.LocationModel
import com.example.data.repos.LocationRepository
import com.example.data.resource.ResponseResult
import com.example.data.resource.apiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Named

class LocationRepositoryImpl(
    @Named("io") private val ioDispatcher: CoroutineDispatcher,
    private val locationDao: LocationDao,
    private val weatherApi: WeatherService
) : LocationRepository {
    override suspend fun searchLocation(query: String): Flow<ResponseResult<List<LocationModel>>> =
        flow {
            emit(ResponseResult.Loading)
            val response = apiCall(operation = {
                weatherApi.searchLocation(query)
            }, converter = {
                it!!.map { res ->
                    LocationModel(
                        name = res?.name.orEmpty(),
                        country = res?.country.orEmpty(),
                        lat = res?.lat ?: 0.0,
                        lon = res?.lon ?: 0.0,
                        state = res?.state.orEmpty(),
                        locName = "${BigDecimal(res?.lat ?: 0.0).setScale(4, RoundingMode.HALF_UP)},${BigDecimal(res?.lon ?: 0.0).setScale(4, RoundingMode.HALF_UP)}"
                    )
                }
            }, isValidResponse = {
                it != null
            })
            emit(response)
            return@flow
        }.flowOn(ioDispatcher)

    override suspend fun getLocalLocations(): List<LocationModel> {
        return withContext(ioDispatcher) {
            locationDao.getAllLocalLocations()
        }
    }

    override suspend fun saveLocation(location: LocationModel): LocationModel {
        return withContext(ioDispatcher) {
            locationDao.insertLocation(location)
            location
        }
    }

    override suspend fun removeLocalLocation(location: LocationModel): LocationModel {
        return withContext(ioDispatcher) {
            locationDao.deleteLocation(location)
            location
        }
    }
}