package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.CurrentWeatherDao
import com.example.data.model.CurrentWeatherModel

@Database(
    entities = [CurrentWeatherModel::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
}