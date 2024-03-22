package com.example.forecast.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.ui.SfDisplayProFontFamily
import com.example.core.ui.color0076FF
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.ForecastModel
import com.example.weatherlib.provideIconSrc
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ForecastDetailScreenWeatherCard(
    modifier: Modifier = Modifier,
    forecast: ForecastModel?,
    currentWeather: CurrentWeatherModel
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                forecast?.list?.firstOrNull()?.weather?.firstOrNull()?.description ?: currentWeather.weather.firstOrNull()?.description.orEmpty(),
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black.copy(alpha = 0.65f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                modifier = Modifier.size(24.dp),
                model = forecast?.list?.firstOrNull()?.weather?.firstOrNull()?.icon.provideIconSrc(),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
            val dateFormat = SimpleDateFormat("hh:mm aa")
            val currentTime = Date()
            val formattedTime = dateFormat.format(currentTime)
            Text(
                formattedTime ?: "--:-- --",
                modifier = Modifier.padding(start = 1.dp),
                textAlign = TextAlign.End,
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = color0076FF
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "${forecast?.list?.firstOrNull()?.temp?.max ?: currentWeather.main.temp}°C",
            modifier = Modifier
                .padding(horizontal = 32.dp),
            fontFamily = SfDisplayProFontFamily,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}