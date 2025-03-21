package com.example.cumulora.features.weather.responsestate

import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastEntity
import com.example.cumulora.data.models.forecast.ForecastResponse

sealed class ForecastStateResponse {

    data object Loading : ForecastStateResponse()

    data class Success(val data: ForecastResponse, val forecastFiveDays: List<Forecast>) :
        ForecastStateResponse()

    data class Failure(val error: String) : ForecastStateResponse()

}