package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ForecastModel

@Dao
interface ForecastModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastModel(forecast: ForecastModel)
    @Query("SELECT * FROM ForecastModel")
    suspend fun getAllSavedForecastModel(): List<ForecastModel>
    @Query("SELECT * FROM ForecastModel WHERE id=:name LIMIT 1")

    suspend fun getSavedForecastModelByName(name: String): ForecastModel?
    @Query("SELECT * FROM ForecastModel WHERE id IN (:name) LIMIT 1")
    suspend fun getSavedForecastModelByNames(name: List<String>): List<ForecastModel>
    @Update
    suspend fun updateForecastModel(forecast: ForecastModel)
    @Delete
    suspend fun deleteForecastModel(forecast: ForecastModel)

    @Query("DELETE FROM ForecastModel")
    suspend fun deleteAll()
}