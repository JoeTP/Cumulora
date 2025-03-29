package com.example.cumulora.features.savedweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedWeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val _mutableSavedWeather = MutableStateFlow<SavedWeatherStateResponse>(SavedWeatherStateResponse.Loading)
    val savedWeatherList: StateFlow<SavedWeatherStateResponse> = _mutableSavedWeather.asStateFlow()

    private val _mutableMessage = MutableStateFlow("")
    val message: StateFlow<String> = _mutableMessage.asStateFlow()

    init {
        getSavedWeather()
    }

    private fun getSavedWeather() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.getSavedWeather().collect{
            _mutableSavedWeather.value = SavedWeatherStateResponse.Success(it)
            }
        }catch (e: Exception) {
            _mutableSavedWeather.value = SavedWeatherStateResponse.Failure(e.message ?: "Unknown error")
        }
    }

    fun deleteSavedWeather(savedWeather: SavedWeather) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteWeather(savedWeather)
    }

    fun restoreDeletedWeather(savedWeather: SavedWeather) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveWeather(savedWeather)
    }



}

