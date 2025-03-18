package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse

class WeatherRemoteDataSourceImpl(private val weatherService: WeatherService) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse? {
        return weatherService.getWeather(lat, lon).body()
    }

    override suspend fun getForecast(lat: Double, lon: Double): ForecastResponse? {
        return weatherService.getForecast(lat, lon).body()
    }
}