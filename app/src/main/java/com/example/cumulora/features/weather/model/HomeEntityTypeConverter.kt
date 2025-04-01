package com.example.cumulora.features.weather.model

import androidx.room.TypeConverter
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.google.gson.Gson

class HomeEntityTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun weatherToJson(weather: WeatherEntity): String {
        return gson.toJson(weather)
    }

    @TypeConverter
    fun jsonToWeather(json: String): WeatherEntity {
        return gson.fromJson(json, WeatherEntity::class.java)
    }

    @TypeConverter
    fun forecastToJson(forecast: ForecastResponse): String {
        return gson.toJson(forecast)
    }

    @TypeConverter
    fun jsonToForecast(json: String): ForecastResponse {
        return gson.fromJson(json, ForecastResponse::class.java)
    }
}