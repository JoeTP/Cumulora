package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WeatherRemoteDataSourceImpl(private val weatherService: WeatherService) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double, unit: String?, lang: String?):
            Flow<WeatherResponse?> {
        return flowOf(weatherService.getWeather(lat, lon, unit ,lang).body())
    }

    override suspend fun getForecast(lat: Double, lon: Double, unit: String): ForecastResponse? {
        return weatherService.getForecast(lat, lon).body()
    }
}