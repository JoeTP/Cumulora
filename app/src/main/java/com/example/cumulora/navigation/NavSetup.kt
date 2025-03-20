package com.example.cumulora.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cumulora.component.MyAppBar
import com.example.cumulora.features.alarm.AlarmScreenUI
import com.example.cumulora.features.onboard.OnBoardingScreenUI
import com.example.cumulora.features.savedweather.SavedWeatherScreenUI
import com.example.cumulora.features.settings.SettingsScreenUI
import com.example.cumulora.features.weather.WeatherScreenUI
import com.example.cumulora.utils.IS_FIRST_TIME_SK
import com.example.cumulora.utils.SharedPrefManager


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavSetup(navController: NavHostController) {

//    val navController = rememberNavController()
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
            Scaffold(topBar = { MyAppBar(navController) }) {padding ->
                WeatherScreenUI(Modifier.padding(padding))
            }
        }

        composable<ScreenRoutes.AlarmScreen> {
            AlarmScreenUI()
            Scaffold (topBar = {
                TopAppBar(title = {
                    Text("Alarms")
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
            }){  }
        }

        composable<ScreenRoutes.SavedWeatherScreen> {
            SavedWeatherScreenUI()
        }

        composable<ScreenRoutes.SettingsScreen> {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text("Settings")
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
            }) {padding ->
                SettingsScreenUI(Modifier.padding(padding))
            }
        }
    }
}