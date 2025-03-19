package com.example.cumulora.features.weather

import com.example.cumulora.data.models.weather.WeatherEntity

sealed class WeatherStateResponse {

    data object Loading : WeatherStateResponse()

    data class Success(val data: WeatherEntity) : WeatherStateResponse()

    data class Failure(val error: String) : WeatherStateResponse()
}