package com.example.forecast.usecase.impl

import com.example.data.model.ForecastModel
import com.example.data.repos.ForeCastRepository
import com.example.data.resource.ResponseResult
import com.example.forecast.usecase.GetForecastUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetForecastDetailsUseCaseImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val forecastRepository: ForeCastRepository
): GetForecastUseCase {

    override suspend fun invoke(lat:Double , lon:Double, cityId: Int): Flow<ResponseResult<ForecastModel>> {
        return flow {
            emit(ResponseResult.Loading)
            val result = forecastRepository.getForecastDetails(lat,lon,cityId)
            emit(result)
            return@flow
        }.flowOn(ioDispatcher)
    }

}