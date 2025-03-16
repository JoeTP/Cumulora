package com.example.cumulora.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cumulora.features.alarm.AlarmScreenUI
import com.example.cumulora.features.onboard.OnBoardingScreenUI
import com.example.cumulora.features.savedweather.SavedWeatherScreenUI
import com.example.cumulora.features.settings.SettingsScreenUI
import com.example.cumulora.features.weather.WeatherScreenUI

@Composable
fun NavSetup() {
    var dummyPref = true
    var startingScreen: ScreenRoutes = if(dummyPref) ScreenRoutes.OnboardingScreen else ScreenRoutes.WeatherScreen
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startingScreen) {


        composable<ScreenRoutes.OnboardingScreen> {
            OnBoardingScreenUI {
                //Show a Popup with the required services like location and alarm and notification
                //save the user preferences here
                navController.navigate(ScreenRoutes.WeatherScreen)
            }
        }

        composable<ScreenRoutes.WeatherScreen> {
            WeatherScreenUI(onNavigateToAlarm = {}, onNavigateToSavedWeather = {})
        }

        composable<ScreenRoutes.AlarmScreen> {
            AlarmScreenUI()
        }

        composable<ScreenRoutes.SavedWeatherScreen> {
            SavedWeatherScreenUI()
        }

        composable<ScreenRoutes.SettingsScreen> {
            SettingsScreenUI()
        }
    }
}