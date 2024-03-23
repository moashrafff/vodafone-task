package com.example.home.usecase.impl

import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.data.repos.CurrentWeatherRepository
import com.example.data.resource.ResponseResult
import com.example.home.usecase.GetCurrentWeatherInformationUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCurrentWeatherInformationUseCaseImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: CurrentWeatherRepository
) : GetCurrentWeatherInformationUseCase {

    override suspend fun invoke(locations: List<LocationModel>): Flow<ResponseResult<Map<LocationModel, CurrentWeatherModel?>>> {
        return flow {
            emit(ResponseResult.Loading)

            val localData = weatherRepository.getWeatherFromDB(locations.map { "${it.lat},${it.lon}" })
            emit(
                ResponseResult.LocalData(
                    locations.associateBy(
                        { it },
                        { loc -> localData.firstOrNull { "${it.coord.lat},${it.coord.lon}" == "${loc.lat},${loc.lon}" } })
                )
            )

            val requests = mutableListOf<Deferred<ResponseResult<CurrentWeatherModel>>>()
            for (i in locations) {
                coroutineScope {
                    val result = async { weatherRepository.getCurrentWeather(lat = i.lat , lon = i.lon , shouldSaveLocally = false) }
                    requests.add(result)
                }
            }

            val results = requests.awaitAll()

            results.firstOrNull { it is ResponseResult.Error }?.let {
                emit(it as ResponseResult.Error)
                return@flow
            }

            emit(
                ResponseResult.Success(
                    results.associateBy(
                        {
                            val location = (it as ResponseResult.Success).data.coord
                           LocationModel(name = "${location.lat},${location.lon}", country = "" ,lat = location.lat , lon = location.lon , state = "")
                        },
                        {
                            (it as ResponseResult.Success).data
                        }
                    )
                )
            )
        }.flowOn(ioDispatcher)
    }

}