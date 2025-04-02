package com.example.cumulora.features.savedweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.utils.FAVORITE_TABLE_NAME

@Entity(tableName = FAVORITE_TABLE_NAME)
@TypeConverters(SavedWeatherTypeConverter::class)
data class SavedWeather(
    @PrimaryKey
    var cityName: String,
    val weather: WeatherEntity?,
)
