package com.example.forecast.di

import com.example.data.repos.ForeCastRepository
import com.example.forecast.usecase.GetForecastUseCase
import com.example.forecast.usecase.impl.GetForecastDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DetailModule {

    @Provides
    @Singleton
    fun providesForecastDetailsUseCase(
        @Named("io") ioDispatcher: CoroutineDispatcher,
        forecastRepository: ForeCastRepository
    ): GetForecastUseCase =
         GetForecastDetailsUseCaseImpl(ioDispatcher, forecastRepository)


}