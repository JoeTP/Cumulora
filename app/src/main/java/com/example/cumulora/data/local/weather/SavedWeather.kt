package com.example.cumulora.data.local.weather

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.utils.FAVORITE_TABLE_NAME

@Entity(tableName = FAVORITE_TABLE_NAME)
@TypeConverters(SavedWeatherTypeConverter::class)
data class SavedWeather(
    @PrimaryKey
    val cityName: String,
//    val units: String,
    val weather: WeatherEntity?,
)
