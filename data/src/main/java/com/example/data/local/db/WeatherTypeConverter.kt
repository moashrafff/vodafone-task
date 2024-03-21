package com.example.data.local.db

import androidx.room.TypeConverter
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {
    @TypeConverter
    fun fromWind(wind: CurrentWeatherModel.Wind): String = wind.toJson()

    @TypeConverter
    fun toWind(json: String) = CurrentWeatherModel.Wind.fromJson(json)

    @TypeConverter
    fun fromCloud(cloud: CurrentWeatherModel.Clouds): String = cloud.toJson()

    @TypeConverter
    fun toCloud(json: String) = CurrentWeatherModel.Clouds.fromJson(json)

    @TypeConverter
    fun fromCoord(coord: CurrentWeatherModel.Coord): String = coord.toJson()

    @TypeConverter
    fun toCoord(json: String) = CurrentWeatherModel.Coord.fromJson(json)

    @TypeConverter
    fun fromMain(main: CurrentWeatherModel.Main): String = main.toJson()

    @TypeConverter
    fun toMain(json: String) = CurrentWeatherModel.Main.fromJson(json)

    @TypeConverter
    fun fromRain(rain: CurrentWeatherModel.Rain): String = rain.toJson()

    @TypeConverter
    fun toRain(json: String) = CurrentWeatherModel.Rain.fromJson(json)

    @TypeConverter
    fun fromSys(sys: CurrentWeatherModel.Sys): String = sys.toJson()

    @TypeConverter
    fun toSys(json: String) = CurrentWeatherModel.Sys.fromJson(json)

    @TypeConverter
    fun fromWeather(weather: CurrentWeatherModel.Weather): String = weather.toJson()

    @TypeConverter
    fun toWeather(json: String) = CurrentWeatherModel.Weather.fromJson(json)

    @TypeConverter
    fun fromLocation(location: LocationModel) = location.toJson()

    @TypeConverter
    fun toLocation(json: String) = LocationModel.fromJson(json)

    @TypeConverter
    fun fromWeather(weather: List<CurrentWeatherModel.Weather>) = Gson().toJson(weather)

    @TypeConverter
    fun toWeatherList(json: String): List<CurrentWeatherModel.Weather> {
        val type = object : TypeToken<List<CurrentWeatherModel.Weather>>() {}.type
        return Gson().fromJson(json, type)
    }

}