package com.example.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.data.dto.LocationDto
import com.google.gson.Gson
import java.math.BigDecimal
import java.math.RoundingMode


/// 46.6174128,-64.1090125
/// 46.6174,-64.109
@Entity
data class LocationModel(
    @PrimaryKey
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val state: String,
    val locName : String
){
    @Ignore
    var isUserCurrentLocation: Boolean = false

    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): LocationModel {
            return Gson().fromJson(json, LocationModel::class.java)
        }
        fun toLocationModel(dto: LocationDto?): LocationModel =
            LocationModel(
                name = dto?.name.orEmpty(),
                country = dto?.country.orEmpty(),
                lat = dto?.lat ?: 0.0,
                lon = dto?.lon ?: 0.0,
                state = dto?.state.orEmpty(),
                locName = "${BigDecimal(dto?.lat ?: 0.0).setScale(4, RoundingMode.HALF_UP)},${BigDecimal(dto?.lon ?: 0.0).setScale(4, RoundingMode.HALF_UP)}"
            )

    }
}