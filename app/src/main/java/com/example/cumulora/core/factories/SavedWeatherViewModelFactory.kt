package com.example.cumulora.core.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.savedweather.SavedWeatherViewModel

class SavedWeatherViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedWeatherViewModel::class.java)) {
            return SavedWeatherViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}