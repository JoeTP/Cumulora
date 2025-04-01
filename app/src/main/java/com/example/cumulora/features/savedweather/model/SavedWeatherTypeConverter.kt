package com.example.cumulora.features.savedweather.model

import androidx.room.TypeConverter
import com.example.cumulora.data.models.weather.WeatherEntity
import com.google.gson.Gson

class SavedWeatherTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWeatherEntity(weather: WeatherEntity?): String? {
        return weather?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toWeatherEntity(weatherResponseString: String?): WeatherEntity? {
        return weatherResponseString?.let { gson.fromJson(it, WeatherEntity::class.java) }
    }
//
//    @TypeConverter
//    fun fromForecastResponse(forecastResponse: ForecastResponse?): String? {
//        return forecastResponse?.let { gson.toJson(it) }
//    }
//
//    @TypeConverter
//    fun toForecastResponse(forecastResponseString: String?): ForecastResponse? {
//        return forecastResponseString?.let { gson.fromJson(it, ForecastResponse::class.java) }
//    }
}