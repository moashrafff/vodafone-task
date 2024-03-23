package com.example.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.core.DeviceSizeType
import com.example.data.model.CurrentWeatherModel
import com.example.home.screen.HomeState


@Composable
fun HomeScreenWeatherList(
    modifier: Modifier = Modifier,
    deviceSizeType: DeviceSizeType,
    onWeatherDetail: (CurrentWeatherModel) -> Unit,
    data: List<HomeState.WeatherCardState>,
) {
    when (deviceSizeType) {
        DeviceSizeType.PORTRAIT -> HomeScreenVerticalWeatherList(modifier, data = data, onWeatherDetail = onWeatherDetail)
        DeviceSizeType.LANDSCAPE -> HomeScreenHorizontalWeatherList(modifier, data = data, onWeatherDetail = onWeatherDetail)
        DeviceSizeType.TABLET -> HomeScreenGridWeatherList(modifier, data = data, onWeatherDetail = onWeatherDetail)
    }
}

@Composable
private fun HomeScreenVerticalWeatherList(
    modifier: Modifier = Modifier,
    onWeatherDetail: (CurrentWeatherModel) -> Unit,
    data: List<HomeState.WeatherCardState>,
) {
    LazyColumn(modifier = modifier) {
        items(count = data.size, key = {
            "${data[it].location.lat},${data[it].location.lon},${data[it].location.locName}"
        }) {
            Spacer(modifier = Modifier.height(4.dp))
            HomeScreenWeatherCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                isCurrentLocation = data[it].location.isUserCurrentLocation,
                location = data[it].location,
                currentWeather = data[it].weather,
                onWeatherDetail = onWeatherDetail
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}


@Composable
private fun HomeScreenHorizontalWeatherList(
    modifier: Modifier = Modifier,
    onWeatherDetail: (CurrentWeatherModel) -> Unit,
    data: List<HomeState.WeatherCardState>,
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(count = data.size, key = {
            "${data[it].location.lat},${data[it].location.lat},${data[it].location.name}"
        }) {
            Spacer(modifier = Modifier.width(12.dp))
            HomeScreenWeatherCard(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                isCurrentLocation = data[it].location.isUserCurrentLocation,
                location = data[it].location,
                currentWeather = data[it].weather,
                onWeatherDetail = onWeatherDetail
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}


@Composable
private fun HomeScreenGridWeatherList(
    modifier: Modifier = Modifier,
    onWeatherDetail: (CurrentWeatherModel) -> Unit,
    data: List<HomeState.WeatherCardState>,
) {

    val configuration = LocalConfiguration.current

    LazyVerticalGrid(
        columns = GridCells.Adaptive((configuration.screenWidthDp / 3).dp)
    ) {
        items(count = data.size, key = {
            data[it].location.name
        }) {
            HomeScreenWeatherCard(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                isCurrentLocation = false,
                location = data[it].location,
                currentWeather = data[it].weather,
                onWeatherDetail = onWeatherDetail
            )
        }
    }
}