package com.example.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.LocationModel
import com.example.data.repos.LocationRepository
import com.example.data.resource.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    @Named("io") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var searchJob: Job? = null
    private var savedLocations: List<LocationModel> = emptyList()

    var uiState =
        MutableStateFlow(SearchState(isLoading = false, message = "", locationList = emptyList()))
        private set

    var searchQuery = mutableStateOf("")
        private set

    init {
        viewModelScope.launch(ioDispatcher) {
            uiState.emit(
                SearchState(
                    isLoading = true,
                    message = "Loading...",
                    locationList = emptyList()
                )
            )
            savedLocations = locationRepository.getLocalLocations()
            uiState.emit(SearchState(isLoading = false, message = "", locationList = emptyList()))
        }
    }

    fun refresh() {
        searchLocations()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        if (query.length >= 3) {
            searchLocations()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onLocationAdd(location: LocationModel) {
        GlobalScope.launch(ioDispatcher) {
            locationRepository.saveLocation(location)
        }
    }

    private fun searchLocations() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(ioDispatcher) {
            delay(1500L)
            if (searchQuery.value.length >= 3) locationRepository.searchLocation(searchQuery.value)
                .collectLatest {
                    when (it) {
                        is ResponseResult.Loading -> {
                            uiState.emit(
                                SearchState(
                                    isLoading = true,
                                    message = "Searching....",
                                    locationList = emptyList()
                                )
                            )
                        }

                        is ResponseResult.Success -> {
                            uiState.emit(SearchState(
                                isLoading = false,
                                message = if (it.data.isEmpty()) "No results \uD83D\uDE41" else "",
                                locationList = it.data.filter { l -> !savedLocations.contains(l) }
                            ))
                        }

                        is ResponseResult.Error -> {
                            uiState.emit(
                                SearchState(
                                    isLoading = false,
                                    message = it.error.errorMessage,
                                    locationList = emptyList()
                                )
                            )
                        }

                        else -> {
                        }
                    }
                }
        }
    }

}