package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

    suspend fun getWeather(lat: Double, lon: Double, unit: String?, lang: String?): Flow<WeatherResponse?>

    suspend fun getForecast(lat: Double, lon: Double, unit: String?, lang: String?): Flow<ForecastResponse?>

}