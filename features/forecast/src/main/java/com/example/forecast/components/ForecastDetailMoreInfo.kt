package com.example.forecast.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.SfDisplayProFontFamily
import com.example.core.ui.color0076FF
import com.example.data.model.CurrentWeatherModel
import com.example.vodafone_task.R
import kotlin.math.roundToInt

@Composable
fun ForecastDetailMoreInfo(
    currentWeather: CurrentWeatherModel
) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp, start = 35.dp, end = 35.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "More Info",
            fontFamily = SfDisplayProFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ForecastDetailInfoCardItem(
                    title = "Humidity",
                    icon = painterResource(id = R.drawable.ic_weather_drop),
                    value = "${currentWeather.main.humidity}%",
                    valueColor = color0076FF
                )
                Spacer(modifier = Modifier.height(36.dp))
                ForecastDetailInfoCardItem(
                    title = "Wind",
                    icon = null,
                    value = "${currentWeather.wind.deg} ${currentWeather.wind.speed.roundToInt()} mph",
                    valueColor = color0076FF
                )
                Spacer(modifier = Modifier.height(36.dp))

                ForecastDetailInfoCardItem(
                    title = "Sea Level",
                    icon = null,
                    value = "${currentWeather.main.seaLevel}",
                    valueColor = color0076FF
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ForecastDetailInfoCardItem(
                    title = "Feels Like",
                    icon = painterResource(id = R.drawable.ic_weather_feels_like),
                    value = "${currentWeather.main.temp}°C",
                    valueColor = color0076FF
                )
                Spacer(modifier = Modifier.height(36.dp))
                ForecastDetailInfoCardItem(
                    title = "Visibility",
                    icon = null,
                    value = "${currentWeather.visibility} kms",
                    valueColor = color0076FF
                )
                Spacer(modifier = Modifier.height(36.dp))
                ForecastDetailInfoCardItem(
                    title = "Pressure",
                    icon = null,
                    value = "${currentWeather.main.pressure} in",
                    valueColor = color0076FF
                )
            }
        }
    }
}

@Composable
fun ForecastDetailInfoCardItem(
    title: String,
    icon: Painter?,
    value: String,
    valueColor: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = SfDisplayProFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (icon != null) {
            Image(
                modifier = Modifier.size(width = 40.dp, height = 36.dp),
                painter = icon,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        Text(
            text = value,
            fontFamily = SfDisplayProFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = valueColor
        )
    }
}