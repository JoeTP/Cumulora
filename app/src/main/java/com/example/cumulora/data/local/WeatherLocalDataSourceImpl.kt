package com.example.cumulora.data.local

import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao,
                                 private val sharedPref: SharedPreferenceHelper) : WeatherLocalDataSource {


    companion object {
        private var INSTANCE: WeatherLocalDataSourceImpl? = null
        fun getInstance(weatherDao: WeatherDao, sharedPref: SharedPreferenceHelper): WeatherLocalDataSourceImpl {
            if (INSTANCE == null) {
                INSTANCE = WeatherLocalDataSourceImpl(weatherDao, sharedPref)
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

    override suspend fun deleteSavedWeather(favoriteWeather: SavedWeather) {
        weatherDao.deleteWeather(favoriteWeather)
    }

    override fun <T> cacheData(key: String, value: T) {
        sharedPref.saveData(key, value)
    }

    override fun <T> getData(key: String, defaultValue: T): T {
       return sharedPref.getData(key, defaultValue)
    }
}