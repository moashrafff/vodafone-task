package com.example.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.SfDisplayProFontFamily
import com.example.core.ui.color0076FF
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import com.example.weather_task.home.R
import com.example.weatherlib.provideIconSrc
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWeatherCard(
    modifier: Modifier = Modifier,
    isCurrentLocation: Boolean,
    location: LocationModel,
    currentWeather: CurrentWeatherModel?,
    onWeatherDetail: (CurrentWeatherModel) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            currentWeather?.let { onWeatherDetail.invoke(it) }
        }
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                currentWeather?.weather?.firstOrNull()?.description ?: "--",
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black.copy(alpha = 0.65f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = currentWeather?.weather?.firstOrNull()?.icon?.provideIconSrc() ?: R.drawable.clear_sky),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))

            val dateFormat = SimpleDateFormat("hh:mm aa")
            val currentTime = Date()
            val formattedTime = dateFormat.format(currentTime)

            Text(
                formattedTime,
                modifier = Modifier.padding(start = 32.dp),
                textAlign = TextAlign.End,
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = color0076FF
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            "${currentWeather?.main?.temp ?: "--"}°C",
            modifier = Modifier
                .padding(horizontal = 32.dp),
            fontFamily = SfDisplayProFontFamily,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                location.name,
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            if (isCurrentLocation) Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}