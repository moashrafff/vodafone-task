package com.example.home.di

import com.example.data.repos.CurrentWeatherRepository
import com.example.home.usecase.GetCurrentWeatherInformationUseCase
import com.example.home.usecase.GetCurrentWeatherLocationByUserLocationUseCase
import com.example.home.usecase.impl.GetCurrentWeatherInformationUseCaseImpl
import com.example.home.usecase.impl.GetCurrentWeatherLocationByUserLocationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    @Singleton
    fun providesCurrentWeatherInformationUseCase(
        @Named("io") ioDispatcher: CoroutineDispatcher,
        weatherRepository: CurrentWeatherRepository
    ): GetCurrentWeatherInformationUseCase {
        return GetCurrentWeatherInformationUseCaseImpl(ioDispatcher, weatherRepository)
    }

    @Provides
    @Singleton
    fun providesCurrentWeatherByUserLocationUseCase(
        @Named("io") ioDispatcher: CoroutineDispatcher,
        weatherRepository: CurrentWeatherRepository
    ): GetCurrentWeatherLocationByUserLocationUseCase {
        return GetCurrentWeatherLocationByUserLocationUseCaseImpl(ioDispatcher, weatherRepository)
    }
}