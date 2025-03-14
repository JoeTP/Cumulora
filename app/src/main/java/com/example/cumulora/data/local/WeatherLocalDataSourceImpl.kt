package com.example.cumulora.data.local

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
}