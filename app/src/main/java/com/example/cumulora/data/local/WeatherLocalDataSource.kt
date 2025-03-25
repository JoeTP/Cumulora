package com.example.cumulora.data.local

import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
//
    suspend fun getSavedWeather() : Flow<List<SavedWeather>>
//
    suspend fun saveWeather(favoriteWeather: SavedWeather)
//
//    suspend fun deleteWeatherAndForecast()
//
//    suspend fun updateWeatherAndForecast(combinedStateResponse: CombinedStateResponse)

}