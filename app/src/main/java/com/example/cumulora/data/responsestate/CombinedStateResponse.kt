package com.example.cumulora.data.responsestate

sealed class CombinedStateResponse {

    object Loading : CombinedStateResponse()

    data class Success(
        val weather: WeatherStateResponse.Success,
        val forecast: ForecastStateResponse.Success
    ) : CombinedStateResponse()

    data class Failure(val error: String) : CombinedStateResponse()
}