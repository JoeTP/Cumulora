package com.example.cumulora.features.weather

import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastEntity

sealed class ForecastStateResponse {

    data object Loading : ForecastStateResponse()

    data class Success(val data: List<Forecast>) : ForecastStateResponse()

    data class Failure(val error: String) : ForecastStateResponse()

}