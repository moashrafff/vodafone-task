package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.LocationModel

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationModel)

    @Query("SELECT * FROM LocationModel")
    suspend fun getAllLocalLocations(): List<LocationModel>

    @Query("SELECT * FROM LocationModel where name=:name LIMIT 1")
    suspend fun getLocalLocationByName(name: String): LocationModel

    @Update
    suspend fun updateLocation(location: LocationModel)

    @Delete
    suspend fun deleteLocation(location: LocationModel)

    @Query("DELETE FROM LocationModel")
    suspend fun deleteAll()

}