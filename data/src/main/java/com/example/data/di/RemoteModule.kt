package com.example.data.di

import android.content.Context
import com.example.data.api.WeatherService
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.repos.CurrentWeatherRepository
import com.example.data.repos.impl.CurrentWeatherRepositoryImpl
import com.example.data.resource.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteModule {
    @Provides
    @Singleton
    fun providesWeatherApi(
        @ApplicationContext context: Context
    ): WeatherService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        }
        val okHttpClient = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 5 * 1024 * 1024))
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(3, TimeUnit.SECONDS)
            .writeTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_WEATHER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(
        @Named("io") ioDispatcher: CoroutineDispatcher,
        weatherDao: CurrentWeatherDao,
        weatherApi: WeatherService
    ): CurrentWeatherRepository {
        return CurrentWeatherRepositoryImpl(ioDispatcher, weatherDao, weatherApi)
    }
}