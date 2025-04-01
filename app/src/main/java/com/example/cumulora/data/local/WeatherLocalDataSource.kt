package com.example.cumulora.data.local

import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.features.weather.model.HomeEntity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    suspend fun getSavedWeather(): Flow<List<SavedWeather>>
    suspend fun saveWeather(favoriteWeather: SavedWeather)
    suspend fun deleteSavedWeather(favoriteWeather: SavedWeather)
    fun <T> cacheData(key: String, value: T)
    fun <T> getData(key: String, defaultValue: T) : T


    suspend fun getAlarms(): Flow<List<Alarm>>
    suspend fun getAlarmById(id: Int): Alarm?
    suspend fun addAlarm(alarm: Alarm)
    suspend fun deleteAlarm(alarm: Alarm)
    suspend fun updateAlarm(alarm: Alarm)

    suspend fun getHomeCachedWeather(): Flow<HomeEntity>
    suspend fun cacheHomeCachedWeather(home: HomeEntity)
}