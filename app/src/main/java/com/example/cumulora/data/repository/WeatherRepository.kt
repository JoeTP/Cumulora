package com.example.cumulora.data.repository

import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {

    suspend fun getWeather(lat : Double, lon : Double, unit: String?, lang: String?): Flow<WeatherResponse?>

    suspend fun getForecast(lat : Double, lon : Double, unit: String?, lang: String?): Flow<ForecastResponse?>

    suspend fun getGeocoder(query: String): Flow<GeocoderResponse?>

    suspend fun getSavedWeather(): Flow<List<SavedWeather>>

    suspend fun saveWeather(favoriteWeather: SavedWeather)

    suspend fun deleteWeather(favoriteWeather: SavedWeather)

    fun notifySettingsChanged()

    fun<T> cacheData(key: String, value: T)

    fun<T> getCachedData(key: String, defaultValue: T) : T

    suspend fun getAlarms(): Flow<List<Alarm>>
    suspend fun getAlarm(id: Int): Alarm?
    suspend fun addAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)
}