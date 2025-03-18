package com.example.cumulora.data.repository

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse


interface WeatherRepository {

    suspend fun getWeather(lat : Double, lon : Double): WeatherResponse?

    suspend fun getForecast(lat : Double, lon : Double): ForecastResponse?

}