package com.example.cumulora.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavSetup() {
    var startingScreen: ScreenRoutes = ScreenRoutes.SplashScreen
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startingScreen) {

        composable<ScreenRoutes.SplashScreen> {
            SplashUI()
        }

        composable<ScreenRoutes.OnboardingScreen> { }

        composable<ScreenRoutes.HomeScreen> { }

        composable<ScreenRoutes.AlarmScreen> { }

        composable<ScreenRoutes.SavedWeatherScreen> { }

        composable<ScreenRoutes.SettingsScreen> { }
    }
}