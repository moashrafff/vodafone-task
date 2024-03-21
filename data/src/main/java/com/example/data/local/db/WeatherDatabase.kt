package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.model.CurrentWeatherModel

@Database(
    entities = [CurrentWeatherModel::class],
    version = 1
)
@TypeConverters(value = [WeatherTypeConverter::class])
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
}