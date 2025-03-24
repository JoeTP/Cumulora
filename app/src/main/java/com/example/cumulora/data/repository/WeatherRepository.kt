package com.example.cumulora.data.repository

import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    suspend fun getWeather(lat : Double, lon : Double, unit: String?, lang: String?): Flow<WeatherResponse?>

    suspend fun getForecast(lat : Double, lon : Double, unit: String?, lang: String?): Flow<ForecastResponse?>

    suspend fun getGeocoder(query: String): Flow<GeocoderResponse?>

//    fun cachingLatLng(lat: Double, lon: Double)

//     fun getLastLatLng(): Pair<Double, Double>

}