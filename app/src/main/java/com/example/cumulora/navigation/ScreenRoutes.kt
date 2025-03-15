package com.example.cumulora.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    @Serializable
    object OnboardingScreen : ScreenRoutes()

    @Serializable
    object WeatherScreen : ScreenRoutes()

    @Serializable
    object AlarmScreen : ScreenRoutes()

    @Serializable
    object SavedWeatherScreen : ScreenRoutes()

    @Serializable
    object SettingsScreen : ScreenRoutes()
}