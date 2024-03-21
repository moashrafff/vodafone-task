package com.example.data.dto


import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("country")
    val country: String?,
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("state")
    val state: String?
)
