package com.example.cumulora.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {
    @Serializable
    object SplashScreen : ScreenRoutes()

    @Serializable
    object OnboardingScreen : ScreenRoutes()

    @Serializable
    object HomeScreen : ScreenRoutes()

    @Serializable
    object AlarmScreen : ScreenRoutes()

    @Serializable
    object SavedWeatherScreen : ScreenRoutes()

    @Serializable
    object SettingsScreen : ScreenRoutes()
}