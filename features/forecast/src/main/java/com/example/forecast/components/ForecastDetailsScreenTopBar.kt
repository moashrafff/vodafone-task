package com.example.forecast.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.SfDisplayProFontFamily
import com.example.data.model.ForecastModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastDetailsScreenTopBar(
    isLoading: Boolean,
    forecast: ForecastModel?,
    name: String,
    onRefresh: () -> Unit,
    onBack: () -> Unit
) {
    TopAppBar(title = {
        Text(
            text = forecast?.city?.name ?: name,
            fontFamily = SfDisplayProFontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }, navigationIcon = {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }, actions = {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                strokeCap = StrokeCap.Round,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(16.dp))
        } else {
            IconButton(onClick = onRefresh) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "", tint = Color.Black)
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))
}