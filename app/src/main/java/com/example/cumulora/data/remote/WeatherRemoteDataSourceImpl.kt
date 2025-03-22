package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class WeatherRemoteDataSourceImpl(private val weatherService: WeatherService) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double, unit: String?, lang: String?):
            Flow<WeatherResponse?> {
        return flowOf(weatherService.getWeather(lat, lon, unit, lang).body())
    }

    override suspend fun getForecast(lat: Double, lon: Double, unit: String?, lang: String?):
            Flow<ForecastResponse?> {
        return flowOf(weatherService.getForecast(lat, lon, unit, lang).body())
    }

    override suspend fun getGeocoder(query: String, limit: Int?): Flow<GeocoderResponse?> {
        return flowOf(weatherService.getGeocoder(query, limit).body())
    }
}