package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.CurrentWeatherModel

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: CurrentWeatherModel)

    @Query("SELECT * FROM CurrentWeatherModel")
    suspend fun getAllSavedWeather(): List<CurrentWeatherModel>

    @Query("SELECT * FROM CurrentWeatherModel WHERE locName=:name LIMIT 1")

    suspend fun getSavedWeatherByName(name: String): CurrentWeatherModel?
    @Query("SELECT * FROM CurrentWeatherModel WHERE locName IN (:name)")
    suspend fun getSavedWeatherByNames(name: List<String>): List<CurrentWeatherModel>

    @Update
    suspend fun updateWeather(weather: CurrentWeatherModel)

    @Delete
    suspend fun deleteWeather(weather: CurrentWeatherModel)

    @Query("DELETE FROM CurrentWeatherModel")
    suspend fun deleteAll()
}