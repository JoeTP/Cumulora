package com.example.cumulora.data.responsestate

import com.example.cumulora.features.savedweather.model.SavedWeather


sealed class SavedWeatherStateResponse {

    object Loading : SavedWeatherStateResponse()

    data class Success(val data: List<SavedWeather>) : SavedWeatherStateResponse()

    data class Failure(val error: String) : SavedWeatherStateResponse()
}