package com.example.cumulora.data.models.alarm

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import com.example.cumulora.data.models.weather.WeatherResponse
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlarmTypeConverter {

    @SuppressLint("NewApi")
    private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

    @SuppressLint("NewApi")
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return localTime?.format(formatter)
    }

    @SuppressLint("NewApi")
    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, formatter) }
    }
}