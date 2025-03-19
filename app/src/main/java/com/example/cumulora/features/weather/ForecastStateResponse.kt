package com.example.cumulora.features.weather

import com.example.cumulora.data.models.forecast.ForecastEntity

sealed class ForecastStateResponse {

    data object Loading : ForecastStateResponse()

    data class Success(val data: ForecastEntity) : ForecastStateResponse()

    data class Failure(val error: String) : ForecastStateResponse()

}