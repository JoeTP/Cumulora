package com.example.cumulora.utils

import com.example.cumulora.data.models.weather.WeatherEntity

sealed class Response {
    data object Loading : Response()
    data class Success<T>(val data: T) : Response()
    data class Failure(val error: Throwable) : Response()
}