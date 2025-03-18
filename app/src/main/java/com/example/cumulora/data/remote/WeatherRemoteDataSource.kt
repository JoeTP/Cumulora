package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse

interface WeatherRemoteDataSource {

    suspend fun getWeather(lat : Double, lon : Double) : WeatherResponse?

    suspend fun getForecast(lat : Double, lon : Double) : ForecastResponse?

}