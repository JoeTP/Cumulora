package com.example.cumulora.data.repository

import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.data.local.weather.WeatherLocalDataSource
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.data.remote.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class WeatherRepositoryImpl private constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : WeatherRepository {


    companion object {
        private val _settingsChanges = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val settingsChanges: SharedFlow<Unit> = _settingsChanges.asSharedFlow()

        @Volatile
        private var instance: WeatherRepositoryImpl? = null

        fun getInstance(
            remoteDataSource: WeatherRemoteDataSource,
            localDataSource: WeatherLocalDataSource
        ): WeatherRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImpl(remoteDataSource, localDataSource).also {
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

    override suspend fun getGeocoder(query: String): Flow<GeocoderResponse?> {
        return remoteDataSource.getGeocoder(query, 1)
    }

    override suspend fun getSavedWeather(): Flow<List<SavedWeather>> {
        return localDataSource.getSavedWeather()
    }

    override suspend fun saveWeather(favoriteWeather: SavedWeather) {
        localDataSource.saveWeather(favoriteWeather)
    }

    override suspend fun deleteWeather(favoriteWeather: SavedWeather) {
        localDataSource.deleteSavedWeather(favoriteWeather)
    }

    override fun <T> cacheData(key: String, value: T) {
        localDataSource.cacheData(key, value)
    }

    override fun <T> getCachedData(key: String, defaultValue: T): T {
        return localDataSource.getData(key, defaultValue) as T
    }

    override suspend fun getAlarms(): Flow<List<Alarm>> {
        return localDataSource.getAlarms()
    }

    override suspend fun getAlarm(id: Int): Alarm? {
        return localDataSource.getAlarmById(id)
    }

    override suspend fun addAlarm(alarm: Alarm) {
        localDataSource.addAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: Alarm) {
        localDataSource.deleteAlarm(alarm)
    }

    override suspend fun updateAlarm(alarm: Alarm) {
        localDataSource.updateAlarm(alarm)
    }


    override fun notifySettingsChanged() {
        _settingsChanges.tryEmit(Unit)
    }

}