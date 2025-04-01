package com.example.cumulora.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.features.weather.model.HomeEntity
import com.example.cumulora.utils.ALARMS_TABLE
import com.example.cumulora.utils.FAVORITE_TABLE_NAME
import com.example.cumulora.utils.HOME_CACHE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM $FAVORITE_TABLE_NAME")
    fun getWeather(): Flow<List<SavedWeather>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(favoriteWeather: SavedWeather)

    @Delete
    suspend fun deleteWeather(favoriteWeather: SavedWeather)

    @Update
    suspend fun updateWeather(favoriteWeather: SavedWeather)

    @Query("SELECT * FROM $ALARMS_TABLE")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM $ALARMS_TABLE WHERE id = :id")
    suspend fun getAlarmById(id: Int): Alarm?

    @Insert
    suspend fun insertAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Update
    suspend fun updateAlarm(alarm: Alarm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeWeather(homeEntity: HomeEntity)

    @Query("SELECT * FROM $HOME_CACHE_TABLE")
    fun getHomeWeather(): Flow<HomeEntity>

}