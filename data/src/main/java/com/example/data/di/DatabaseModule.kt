package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.local.dao.LocationDao
import com.example.data.local.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context = context, WeatherDatabase::class.java, "WeatherDB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesCurrentWeatherDao(weatherDatabase: WeatherDatabase): CurrentWeatherDao {
        return weatherDatabase.currentWeatherDao()
    }

    @Provides
    @Singleton
    fun providesLocationDao(weatherDatabase: WeatherDatabase): LocationDao {
        return weatherDatabase.locationDao()
    }
}