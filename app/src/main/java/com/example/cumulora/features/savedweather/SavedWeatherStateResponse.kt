package com.example.cumulora.features.savedweather

import com.example.cumulora.data.local.weather.SavedWeather


sealed class SavedWeatherStateResponse {

    object Loading : SavedWeatherStateResponse()

    data class Success(val data: List<SavedWeather>) : SavedWeatherStateResponse()

    data class Failure(val error: String) : SavedWeatherStateResponse()
}