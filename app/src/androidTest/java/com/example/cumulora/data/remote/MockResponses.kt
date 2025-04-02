package com.example.cumulora.data.remote

import com.example.cumulora.data.models.common.Clouds
import com.example.cumulora.data.models.common.Coord
import com.example.cumulora.data.models.common.Wind
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.Weather
import com.example.cumulora.data.models.weather.WeatherResponse


object WeatherResponseStub {
    fun create(): WeatherResponse = WeatherResponse(
        id = 1481,
        name = "New York",
        coord = Coord(lat = 40.40, lon = 40.40),
        weatherList = listOf(Weather(id = 5678, main = "litora", description = "litora", icon = "litora")),
        base = "litora",
        main = WeatherResponse.Main(
            temp = 32.33,
            feelsLike = 34.35,
            tempMin = 36.37,
            tempMax = 38.39,
            pressure = 6071,
            humidity = 4011,
            seaLevel = 5247,
            groundLevel = 4640
        ),
        visibility = 5126,
        wind = Wind(speed = 40.41, deg = 7730, gust = 42.43),
        rain = null,
        clouds = Clouds(all = 5380),
        dt = 6498,
        sys = WeatherResponse.Sys(
            type = 6132,
            id = 7702,
            country = "Gabon",
            sunrise = 1758,
            sunset = 2273
        ),
        timezone = 2708,
        cod = 5679

    )
}

object ForecastResponseStub {
    fun create(): ForecastResponse = ForecastResponse(
        forecastList = listOf(), city = Forecast.City(
            id = 4228,
            name = "New York",
            coord = Coord(lat = 40.40, lon = 40.40),
            country = "Ethiopia",
            population = 9433,
            timezone = 3519,
            sunrise = 6687,
            sunset = 7574
        )
    )
}