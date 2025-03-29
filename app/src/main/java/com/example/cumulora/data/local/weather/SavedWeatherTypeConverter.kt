package com.example.cumulora.data.local.weather

import androidx.room.TypeConverter
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import com.google.gson.Gson

class SavedWeatherTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWeatherResponse(weatherResponse: WeatherResponse?): String? {
        return weatherResponse?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toWeatherResponse(weatherResponseString: String?): WeatherResponse? {
        return weatherResponseString?.let { gson.fromJson(it, WeatherResponse::class.java) }
    }

    @TypeConverter
    fun fromForecastResponse(forecastResponse: ForecastResponse?): String? {
        return forecastResponse?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toForecastResponse(forecastResponseString: String?): ForecastResponse? {
        return forecastResponseString?.let { gson.fromJson(it, ForecastResponse::class.java) }
    }
}