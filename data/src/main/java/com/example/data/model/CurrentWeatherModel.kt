package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.dto.CurrentWeatherDto
import com.example.data.model.CurrentWeatherModel.Clouds.Companion.toCloudsModel
import com.example.data.model.CurrentWeatherModel.Coord.Companion.toCoordModel
import com.example.data.model.CurrentWeatherModel.Main.Companion.toMainModel
import com.example.data.model.CurrentWeatherModel.Rain.Companion.toRainModel
import com.example.data.model.CurrentWeatherModel.Sys.Companion.toSysModel
import com.example.data.model.CurrentWeatherModel.Weather.Companion.toWeatherModel
import com.example.data.model.CurrentWeatherModel.Wind.Companion.toWindModel
import com.google.gson.Gson

@Entity
data class CurrentWeatherModel(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    @PrimaryKey
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) {
    fun toJson() = Gson().toJson(this)
    data class Clouds(
        val all: Int
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Clouds {
                return Gson().fromJson(json, Clouds::class.java)
            }
            fun toCloudsModel(clouds: CurrentWeatherDto.Clouds?): Clouds = Clouds(all = clouds?.all ?: 0)
        }
    }

    data class Coord(
        val lat: Double,
        val lon: Double
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Coord {
                return Gson().fromJson(json, Coord::class.java)
            }
            fun toCoordModel(coord : CurrentWeatherDto.Coord?): Coord =
                Coord(lat = coord?.lat ?: 0.0, lon = coord?.lon ?: 0.0)
        }
    }

    data class Main(
        val feelsLike: Double,
        val grndLevel: Int,
        val humidity: Int,
        val pressure: Int,
        val seaLevel: Int,
        val temp: Double,
        val tempMax: Double,
        val tempMin: Double
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Main {
                return Gson().fromJson(json, Main::class.java)
            }
            fun toMainModel(main : CurrentWeatherDto.Main?): Main = Main(
                feelsLike = main?.feelsLike ?: 0.0,
                grndLevel = main?.grndLevel ?: 0,
                humidity = main?.humidity ?: 0,
                pressure = main?.pressure ?: 0,
                seaLevel = main?.seaLevel ?: 0,
                temp = main?.temp ?: 0.0,
                tempMax = main?.tempMax ?: 0.0,
                tempMin = main?.tempMin ?: 0.0
            )
        }
    }

    data class Rain(
        val h: Double
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Rain {
                return Gson().fromJson(json, Rain::class.java)
            }
            fun toRainModel(rain: CurrentWeatherDto.Rain?): Rain = Rain(h = rain?.h ?: 0.0)
        }
    }

    data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Sys {
                return Gson().fromJson(json, Sys::class.java)
            }
            fun toSysModel(sys : CurrentWeatherDto.Sys?): Sys = Sys(
                country = sys?.country.orEmpty(),
                id = sys?.id ?: 0,
                sunrise = sys?.sunrise ?: 0,
                sunset = sys?.sunset ?: 0,
                type = sys?.type ?: 0
            )
        }
    }

    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Weather {
                return Gson().fromJson(json, Weather::class.java)
            }
            fun CurrentWeatherDto.Weather.toWeatherModel(): Weather = Weather(
                description = description.orEmpty(),
                icon = icon.orEmpty(),
                id = id ?: 0,
                main = main.orEmpty()
            )
        }
    }

    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    ) {
        fun toJson(): String {
            return Gson().toJson(this)
        }
        companion object {
            fun fromJson(json: String): Wind {
                return Gson().fromJson(json, Wind::class.java)
            }
            fun toWindModel(wind : CurrentWeatherDto.Wind?): Wind = Wind(
                deg = wind?.deg ?: 0,
                gust = wind?.gust ?: 0.0,
                speed = wind?.speed ?: 0.0
            )
        }
    }

    companion object {
        fun fromJson(json: String) = Gson().fromJson(json, CurrentWeatherModel::class.java)
        fun toCurrentModel (dto : CurrentWeatherDto?): CurrentWeatherModel = CurrentWeatherModel(
            base = dto?.base.orEmpty(),
            clouds = toCloudsModel(dto?.clouds),
            cod = dto?.cod ?: 0,
            coord = toCoordModel(dto?.coord),
            dt = dto?.dt ?: 0,
            id = dto?.id ?: 0,
            main = toMainModel(dto?.main),
            name = dto?.name.orEmpty(),
            rain = toRainModel(dto?.rain),
            sys = toSysModel(dto?.sys),
            timezone = dto?.timezone ?: 0,
            visibility = dto?.visibility ?: 0,
            weather = dto?.weather?.mapNotNull {
                it?.toWeatherModel()
            }.orEmpty(),
            wind = toWindModel(dto?.wind)
        )
    }
}