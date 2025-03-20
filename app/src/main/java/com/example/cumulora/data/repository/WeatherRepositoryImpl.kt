package com.example.cumulora.data.repository

import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.data.remote.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class WeatherRepositoryImpl private constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
//    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {

    companion object {
        @Volatile
        private var instance: WeatherRepositoryImpl? = null

        fun getInstance(
            remoteDataSource: WeatherRemoteDataSource,
//            localDataSource: WeatherLocalDataSource
        ): WeatherRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImpl(remoteDataSource /*localDataSource*/).also {
                    instance = it
                }
            }
        }
    }

    override suspend fun getWeather(lat: Double, lon: Double, unit: String?, lang: String?):
            Flow<WeatherResponse?> {
        return remoteDataSource.getWeather(
            lat = lat,
            lon = lon,
            unit = unit,
            lang = lang
        )
    }

    override suspend fun getForecast(lat: Double, lon: Double, unit: String?, lang: String?):
            Flow<ForecastResponse?> {
        return remoteDataSource.getForecast(
            lat = lat,
            lon = lon,
            unit = unit,
            lang = lang
        )
    }


}