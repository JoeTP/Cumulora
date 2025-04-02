package com.example.cumulora.features.savedweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.responsestate.SavedWeatherStateResponse
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.utils.DEFAULT_UNITS
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON
import com.example.cumulora.utils.UNITS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedWeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val _mutableSavedWeather =
        MutableStateFlow<SavedWeatherStateResponse>(SavedWeatherStateResponse.Loading)
    val savedWeatherList: StateFlow<SavedWeatherStateResponse> = _mutableSavedWeather.asStateFlow()

//    private val _mutableMessage = MutableStateFlow("")
//    val message: StateFlow<String> = _mutableMessage.asStateFlow()

    init {
        getSavedWeather()
    }

    private fun getSavedWeather() = viewModelScope.launch {
        try {
            println("TRYING TO GET SAVED WEATHER")
            repo.getSavedWeather().collect {
                println("COLLECTING SAVED WEATHER SIZE: ${it.size}")
                _mutableSavedWeather.value = SavedWeatherStateResponse.Success(it)
            }
        } catch (e: Exception) {
            println("CATCHING ERROR: ${e.message}")
            _mutableSavedWeather.value = SavedWeatherStateResponse.Failure(e.message ?: "Unknown error")
        }
    }

    fun deleteSavedWeather(savedWeather: SavedWeather) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteWeather(savedWeather)
    }

    fun restoreDeletedWeather(savedWeather: SavedWeather) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveWeather(savedWeather)
    }

    fun getUnits() = repo.getCachedData(UNITS, DEFAULT_UNITS)


    fun selectSavedWeather(lat: Double, lon: Double) {
        repo.cacheData(LAST_LAT, lat.toString())
        repo.cacheData(LAST_LON, lon.toString())
    }

}

