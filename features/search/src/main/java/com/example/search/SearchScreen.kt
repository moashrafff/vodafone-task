package com.example.search

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ConnectivityObserver
import com.example.core.DeviceSizeType
import com.example.core.components.UiLoader
import com.example.core.ui.SfDisplayProFontFamily
import com.example.search.components.SearchListCard
import com.example.search.components.SearchScreenSearchBar
import com.example.search.components.SearchTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    deviceSizeType: DeviceSizeType,
    connectivityState: ConnectivityObserver.Status,
    onBack: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {

    LaunchedEffect(key1 = connectivityState, key2 = searchViewModel.searchQuery.value) {
        if (connectivityState == ConnectivityObserver.Status.NetworkAvailable) searchViewModel.refresh()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        when (deviceSizeType) {
            DeviceSizeType.PORTRAIT -> {
                SearchScreenPortrait(modifier, searchViewModel = searchViewModel, onBack = onBack)
            }

            DeviceSizeType.LANDSCAPE -> {
                SearchScreenLandscape(modifier, searchViewModel = searchViewModel)
            }

            DeviceSizeType.TABLET -> {
                SearchScreenLandscape(modifier, searchViewModel = searchViewModel)
            }
        }
    }
}

@Composable
fun SearchScreenPortrait(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    searchViewModel: SearchViewModel
) {
    val uiState by searchViewModel.uiState.collectAsState()
    val searchQuery by searchViewModel.searchQuery

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            SearchTopBar(onBack = onBack)
        }
        item {
            SearchScreenSearchBar(query = searchQuery, isLoading = uiState.isLoading, onSearchQueryChanged = searchViewModel::onSearchQueryChanged)
        }
        if (uiState.isLoading) {
            item {
                UiLoader()
            }
        }
        if (uiState.message.isNotBlank()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    text = uiState.message,
                    textAlign = TextAlign.Center,
                    fontFamily = SfDisplayProFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
        if (uiState.locationList.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(count = uiState.locationList.size) {
                SearchListCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    location = uiState.locationList[it],
                    onAdd = {
                        searchViewModel.onLocationAdd(uiState.locationList[it])
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreenLandscape(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel
) {

    val uiState by searchViewModel.uiState.collectAsState()
    val searchQuery by searchViewModel.searchQuery
    val configuration = LocalConfiguration.current

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        SearchScreenSearchBar(query = searchQuery, isLoading = uiState.isLoading, onSearchQueryChanged = searchViewModel::onSearchQueryChanged)
        Spacer(modifier = Modifier.height(8.dp))
        if (uiState.isLoading) {
            UiLoader()
        }
        if (uiState.message.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = uiState.message,
                textAlign = TextAlign.Center,
                fontFamily = SfDisplayProFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
        if (uiState.locationList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive((configuration.screenWidthDp / 2).dp)
            ) {
                items(count = uiState.locationList.size) {
                    SearchListCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        location = uiState.locationList[it],
                        onAdd = {
                            searchViewModel.onLocationAdd(uiState.locationList[it])
                        }
                    )
                }
            }
        }
    }
}