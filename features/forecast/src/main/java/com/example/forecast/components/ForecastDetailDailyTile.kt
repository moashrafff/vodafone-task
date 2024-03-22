package com.example.forecast.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.ui.SfDisplayProFontFamily
import com.example.core.ui.color7F7F7F
import com.example.data.model.ForecastModel

@Composable
fun ForecastDetailDailyTile(
    forecastDay: ForecastModel.Days
) {

    var hourlyDetailsVisible by remember {
        mutableStateOf(false)
    }

    Column {
        Column(
            modifier = Modifier
                .clickable {
                    hourlyDetailsVisible = !hourlyDetailsVisible
                }
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.weight(1f))
                AsyncImage(
                    modifier = Modifier.size(32.dp),
                    model = forecastDay.weather.firstOrNull()?.icon,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(13.dp))
                Text(
                    "${forecastDay.temp.max.toInt()}°",
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(19.dp))
                Text(
                    "${forecastDay.temp.min.toInt()}°",
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = color7F7F7F
                )
                Spacer(modifier = Modifier.width(35.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}