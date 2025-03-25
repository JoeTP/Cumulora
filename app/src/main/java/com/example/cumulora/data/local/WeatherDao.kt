package com.example.cumulora.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cumulora.utils.FAVORITE_TABLE_NAME
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

}