package com.example.cumulora.features.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.utils.HOME_CACHE_TABLE

@Entity(tableName = HOME_CACHE_TABLE)
@TypeConverters(HomeEntityTypeConverter::class)
data class HomeEntity(
    @PrimaryKey
    val id: Int,
    val weather: WeatherEntity,
    val forecast: ForecastResponse
)