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
    data class Clouds(
        val all: Int
    ) {
        companion object {
            fun toCloudsModel(clouds: CurrentWeatherDto.Clouds?): Clouds = Clouds(all = clouds?.all ?: 0)
        }
    }

    data class Coord(
        val lat: Double,
        val lon: Double
    ) {
        companion object {
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
        companion object {
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
        companion object {
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
        companion object {
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
        companion object {
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
        companion object {
            fun toWindModel(wind : CurrentWeatherDto.Wind?): Wind = Wind(
                deg = wind?.deg ?: 0,
                gust = wind?.gust ?: 0.0,
                speed = wind?.speed ?: 0.0
            )
        }
    }

    companion object {
        fun CurrentWeatherDto.toCurrentWeatherModel(): CurrentWeatherModel = CurrentWeatherModel(
            base = base.orEmpty(),
            clouds = toCloudsModel(clouds),
            cod = cod ?: 0,
            coord = toCoordModel(coord),
            dt = dt ?: 0,
            id = id ?: 0,
            main = toMainModel(main),
            name = name.orEmpty(),
            rain = toRainModel(rain),
            sys = toSysModel(sys),
            timezone = timezone ?: 0,
            visibility = visibility ?: 0,
            weather = weather?.mapNotNull {
                it?.toWeatherModel()
            }.orEmpty(),
            wind = toWindModel(wind)
        )
    }
}