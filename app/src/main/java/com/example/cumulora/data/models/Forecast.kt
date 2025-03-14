package com.example.cumulora.data.models

import com.google.gson.annotations.SerializedName


data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    @SerializedName(value = "dt_txt")
    val dtTxt: String,
    val city: City,
    val cnt: Int,
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
        @SerializedName(value = "see_level")
        val seaLevel: Int,
        @SerializedName(value = "grnd_level")
        val grndLevel: Int,
        val humidity: Int,
        @SerializedName(value = "temp_kf")
        val tempKf: Double
    )


    data class Rain(
        @SerializedName(value = "3h")
        val perHours: Double
    )

    data class Sys(
        val pod: String
    )

    data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String,
        val population: Int,
        val timezone: Int,
        val sunrise: Long,
        val sunset: Long
    )


}