package com.example.cumulora.data.repository

import com.example.cumulora.data.models.Forecast
import com.example.cumulora.data.models.ForecastResponse
import com.example.cumulora.data.models.Weather
import com.example.cumulora.data.models.WeatherResponse


interface WeatherRepository {

    suspend fun getWeather(lat : Double, lon : Double): WeatherResponse?

    suspend fun getForecast(lat : Double, lon : Double): ForecastResponse?

}