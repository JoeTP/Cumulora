package com.example.cumulora.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    @Serializable
    object Onboarding : ScreenRoutes()

    @Serializable
    object Weather : ScreenRoutes()

    @Serializable
    class Alarm(val cityName: String) : ScreenRoutes()

    @Serializable
    object SavedWeather : ScreenRoutes()

    @Serializable
    object Settings : ScreenRoutes()

    @Serializable
    object Map : ScreenRoutes()

}