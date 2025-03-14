package com.example.cumulora.data.remote

import com.example.cumulora.data.models.Forecast
import com.example.cumulora.data.models.ForecastResponse
import com.example.cumulora.data.models.WeatherResponse

class WeatherRemoteDataSourceImpl(private val weatherService: WeatherService) : WeatherRemoteDataSource {

    companion object {
        @Volatile
        private var instance: WeatherRemoteDataSourceImpl? = null

        fun getInstance(weatherService: WeatherService): WeatherRemoteDataSourceImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherRemoteDataSourceImpl(weatherService).also {
                    instance = it
                }
            }
        }
    }

    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse? {
        return weatherService.getWeather(lat, lon).body()
    }

    override suspend fun getForecast(lat: Double, lon: Double): ForecastResponse? {
        return weatherService.getForecast(lat, lon).body()
    }
}