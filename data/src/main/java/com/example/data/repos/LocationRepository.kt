package com.example.data.repos

import com.example.data.model.LocationModel
import com.example.data.resource.ResponseResult
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun searchLocation(query: String): Flow<ResponseResult<List<LocationModel>>>
    suspend fun getLocalLocations(): List<LocationModel>

    suspend fun saveLocation(location: LocationModel): LocationModel

    suspend fun removeLocalLocation(location: LocationModel): LocationModel

}