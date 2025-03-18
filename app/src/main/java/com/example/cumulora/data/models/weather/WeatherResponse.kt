package com.example.cumulora.data.models.weather

import com.example.cumulora.data.models.common.Clouds
import com.example.cumulora.data.models.common.Coord
import com.example.cumulora.data.models.common.Wind
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val id: Int,
    val coord: Coord,
    @SerializedName(value = "weather")
    val weatherList: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain?,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val name: String,
    val cod: Int
) {
    data class Main(
        val temp: Double,
        @SerializedName(value = "feels_like")
        val feelsLike: Double,
        @SerializedName(value = "temp_min")
        val tempMin: Double,
        @SerializedName(value = "temp_max")
        val tempMax: Double,
        val pressure: Int,
        val humidity: Int,
        @SerializedName(value = "see_level")
        val seaLevel: Int,
        @SerializedName(value = "grnd_level")
        val groundLevel: Int
    )

    data class Rain(
        @SerializedName(value = "1h")
        val perHours: Double
    )

    data class Sys(
        val type: Int, val id: Int, val country: String, val sunrise: Long, val sunset: Long
    )
}





