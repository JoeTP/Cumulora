package com.example.cumulora.data.models.forecast

import com.example.cumulora.data.models.weather.Weather


data class ForecastEntity(
    val dt: Long,
    val dtTxt: String,
    val main: String,
    val clouds: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val windDegree: Int,
    val pressure: Int,
    val icon: String,
    val description: String
)