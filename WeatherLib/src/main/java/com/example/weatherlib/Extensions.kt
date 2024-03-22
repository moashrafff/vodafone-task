package com.example.weatherlib

import androidx.annotation.DrawableRes
import com.example.vodafone_task.R

fun String?.provideIconSrc() :Int {
      return when (this) {
         "01d", "01n" -> R.drawable.clear_sky
         "02d", "02n" -> R.drawable.few_clouds
         "03d", "03n" -> R.drawable.rain
         "04d", "04n" -> R.drawable.broken_clouds
         "09d", "09n" -> R.drawable.shower_rain
         "10d", "10n" -> R.drawable.rain
         "11d", "11n" -> R.drawable.thunderstorm
         "13d", "13n" -> R.drawable.snow
         "50d", "50n" -> R.drawable.mist
         else -> R.drawable.clear_sky
     }
 }