package com.example.cumulora.data.local

import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.features.weather.model.HomeEntity
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

    override suspend fun getAlarms(): Flow<List<Alarm>> {
        return weatherDao.getAllAlarms()
    }

    override suspend fun getAlarmById(id: Int): Alarm? {
        return weatherDao.getAlarmById(id)
    }

    override suspend fun addAlarm(alarm: Alarm) {
        weatherDao.insertAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        weatherDao.deleteAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        weatherDao.updateAlarm(alarm)
    }

    override suspend fun getHomeCachedWeather(): Flow<HomeEntity> {
        return weatherDao.getHomeWeather()
    }

    override suspend fun cacheHomeCachedWeather(home: HomeEntity) {
        weatherDao.insertHomeWeather(home)
    }
}