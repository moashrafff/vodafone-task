package com.example.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.SfDisplayProFontFamily
import com.example.core.ui.colorA6A6A6
import com.example.data.model.CurrentWeatherModel
import com.example.data.model.LocationModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListCard(
    modifier: Modifier = Modifier,
    location: LocationModel,
    onAdd: () -> Unit,
    onWeatherDetail: (String) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            location.let {
                GlobalScope.launch {
                    onAdd.invoke()
                    onWeatherDetail.invoke("${it.lat},${it.lon}")
                }
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = location.name,
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 22.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${if (location.state.isNotBlank()) location.state + "," else ""} ${if (location.state.isNotBlank()) location.country else ""}",
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = colorA6A6A6
                )
            }
            TextButton(onClick = onAdd) {
                Text(
                    text = "Add",
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}