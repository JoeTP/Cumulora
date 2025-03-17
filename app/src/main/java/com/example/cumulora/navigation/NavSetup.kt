package com.example.cumulora.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cumulora.features.alarm.AlarmScreenUI
import com.example.cumulora.features.onboard.OnBoardingScreenUI
import com.example.cumulora.features.savedweather.SavedWeatherScreenUI
import com.example.cumulora.features.settings.SettingsScreenUI
import com.example.cumulora.features.weather.WeatherScreenUI
import com.example.cumulora.utils.IS_FIRST_TIME_SK
import com.example.cumulora.utils.SharedPrefManager


@Composable
fun NavSetup() {

    val navController = rememberNavController()
    val shared = SharedPrefManager.getInstance()
    val isFirstTime = shared.getBoolean(IS_FIRST_TIME_SK, true)
    val startingScreen = if (isFirstTime) ScreenRoutes.OnboardingScreen else ScreenRoutes.WeatherScreen

    NavHost(navController = navController, startDestination = startingScreen) {

        composable<ScreenRoutes.OnboardingScreen> {
            OnBoardingScreenUI {
                shared.saveData(IS_FIRST_TIME_SK, false)
                Log.i("TAG", "SAVE ATTEMPT = $isFirstTime ")
                navController.navigate(ScreenRoutes.WeatherScreen)
            }
        }

        composable<ScreenRoutes.WeatherScreen> {
            WeatherScreenUI(onNavigateToAlarm = {}, onNavigateToSavedWeather = {})
//            WeatherScreenUIPrev()
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