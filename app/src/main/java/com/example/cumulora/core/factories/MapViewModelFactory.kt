package com.example.cumulora.core.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.map.MapViewModel
import com.google.android.libraries.places.api.net.PlacesClient

class MapViewModelFactory(private val repo: WeatherRepository, private val placesClient: PlacesClient) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(repo, placesClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}