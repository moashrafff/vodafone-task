package com.example.search

import com.example.data.model.LocationModel

data class SearchState(
    var isLoading: Boolean,
    var message: String,
    var locationList: List<LocationModel>
)