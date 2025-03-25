package com.example.cumulora.data.local

import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao) : WeatherLocalDataSource {


    companion object {
        private var INSTANCE: WeatherLocalDataSourceImpl? = null
        fun getInstance(weatherDao: WeatherDao): WeatherLocalDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE = WeatherLocalDataSourceImpl(weatherDao)
            }
            return INSTANCE!!
        }
    }

    override suspend fun getSavedWeather(): Flow<List<SavedWeather>> {
        return weatherDao.getWeather()
    }

    override suspend fun saveWeather(favoriteWeather: SavedWeather) {
        weatherDao.insertWeather(favoriteWeather)
    }

}