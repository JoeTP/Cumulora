package com.example.cumulora.data.models.weather

data class WeatherEntity(
    val lat: Double,
    val lon: Double,
    val currentTemp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val feelsLike: Double,
    val currentDate: String,
    val currentTime: String,
    val humidity: Int,
    val windSpeed: Double,
    val windDegree: Int,
    val pressure: Int,
    val clouds: Int,
    val city: String,
    val icon: String,
    val description: String,
    val sunRise :Long,
    val sunSet :Long
)