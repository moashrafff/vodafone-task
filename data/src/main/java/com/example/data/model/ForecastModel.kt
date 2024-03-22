package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.dto.ForecastDto
import com.google.gson.Gson
import java.util.Calendar
import java.util.Date

@Entity
data class ForecastModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Days>,
    val message: Double
) {

    @PrimaryKey
    var id = city.id

    data class City(
        val coord: Coord,
        val country: String,
        val id: Int,
        val name: String,
        val population: Int,
        val timezone: Int
    ) {
        fun toJson() = Gson().toJson(this)
        data class Coord(
            val lat: Double,
            val lon: Double
        ) {
            companion object {


                fun toCoordModel(dtoModel: ForecastDto.City.Coord?): Coord = Coord(
                    lat = dtoModel?.lat ?: 0.0,
                    lon = dtoModel?.lon ?: 0.0
                )
            }
        }

        companion object {

            fun fromJson(json: String): City {
                return Gson().fromJson(json, City::class.java)
            }
            fun toCityModel(dtoCity: ForecastDto.City?): City = City(
                coord = Coord.toCoordModel(dtoCity?.coord),
                country = dtoCity?.country.orEmpty(),
                id = dtoCity?.id ?: 0,
                name = dtoCity?.name.orEmpty(),
                population = dtoCity?.population ?: 0,
                timezone = dtoCity?.timezone ?: 0
            )
        }
    }

    data class Days(
        val clouds: Int,
        val deg: Int,
        val dt: String,
        val feelsLike: FeelsLike,
        val gust: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Double,
        val speed: Double,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val weather: List<Weather>
    ) {
        data class FeelsLike(
            val day: Double,
            val eve: Double,
            val morn: Double,
            val night: Double
        ) {
            companion object {
                fun toFeelsLikeModel(dtoModel: ForecastDto.Days.FeelsLike?): FeelsLike = FeelsLike(
                    day = dtoModel?.day ?: 0.0,
                    eve = dtoModel?.eve ?: 0.0,
                    morn = dtoModel?.morn ?: 0.0,
                    night = dtoModel?.night ?: 0.0
                )
            }
        }

        data class Temp(
            val day: Double,
            val eve: Double,
            val max: Double,
            val min: Double,
            val morn: Double,
            val night: Double
        ) {
            companion object {
                fun toFeelsLikeModel(dtoModel: ForecastDto.Days.Temp?): Temp = Temp(
                    day = dtoModel?.day ?: 0.0,
                    eve = dtoModel?.eve ?: 0.0,
                    max = dtoModel?.max ?: 0.0,
                    min = dtoModel?.min ?: 0.0,
                    morn = dtoModel?.morn ?: 0.0,
                    night = dtoModel?.night ?: 0.0
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
                fun toWeatherModel(dtoModel: ForecastDto.Days.Weather?): Weather = Weather(
                    description = dtoModel?.description.orEmpty(),
                    icon = dtoModel?.icon.orEmpty(),
                    id = dtoModel?.id ?: 0,
                    main = dtoModel?.main.orEmpty()
                )
            }
        }

        companion object {
            fun unixTimestampToDate(unixTimestamp: Long): Date {
                return Date(unixTimestamp * 1000) // Multiply by 1000 to convert seconds to milliseconds
            }

            // Function to get the day name from a date
            fun getDayName(date: Date): String {
                val days = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
                val calendar = Calendar.getInstance()
                calendar.time = date
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                return days[dayOfWeek - 1]
            }
            fun toDayModel(dtoModel: ForecastDto.Days?): Days = Days(
                clouds = dtoModel?.clouds ?:0,
                deg = dtoModel?.deg ?:0,
                dt = getDayName(unixTimestampToDate(dtoModel?.dt?.toLong()!!)),
                feelsLike = FeelsLike.toFeelsLikeModel(dtoModel.feelsLike),
                gust = dtoModel?.gust ?: 0.0 ,
                humidity = dtoModel?.humidity ?:0 ,
                pop = dtoModel?.pop ?:0.0,
                pressure = dtoModel?.pressure ?:0,
                rain = dtoModel?.rain ?: 0.0,
                speed = dtoModel?.speed ?: 0.0,
                sunrise = dtoModel?.sunrise ?: 0,
                sunset =dtoModel?.sunset ?:0,
                temp = Temp.toFeelsLikeModel(dtoModel?.temp),
                weather = dtoModel?.weather?.mapNotNull {
                    Weather.toWeatherModel(it)
                }.orEmpty()
            )
        }
    }

    companion object {
        fun toForeCastModel(forecastDto: ForecastDto): ForecastModel = ForecastModel(
            city = City.toCityModel(forecastDto.city),
            cnt = forecastDto.cnt ?:0,
            cod = forecastDto.cod.orEmpty() ,
            list = forecastDto.list?.mapNotNull { Days.toDayModel(it) }.orEmpty() ,
            message = forecastDto.message ?: 0.0
        )
    }
}