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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.ui.SfDisplayProFontFamily
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import java.text.SimpleDateFormat
import java.util.Locale

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
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                modifier = Modifier.size(32.dp),
                model = currentWeather?.weather?.firstOrNull()?.icon,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.weight(1f))
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