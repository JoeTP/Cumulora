package com.example.cumulora.features.weather.responsestate

import com.example.cumulora.data.models.weather.WeatherEntity

sealed class WeatherStateResponse {

    data object Loading : WeatherStateResponse()

    data class Success(val data: WeatherEntity) : WeatherStateResponse()

    data class Failure(val error: String) : WeatherStateResponse()
}

sealed class CombinedStateResponse {

    object Loading : CombinedStateResponse()

    data class Success(
        val weather: WeatherStateResponse.Success,
        val forecast: ForecastStateResponse.Success
    ) : CombinedStateResponse()

    data class Failure(val error: String) : CombinedStateResponse()
}