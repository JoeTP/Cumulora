package com.example.cumulora.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocationAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.cumulora.R
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.features.alarm.AlarmScreenUI
import com.example.cumulora.features.map.MapScreenUI
import com.example.cumulora.features.onboard.OnBoardingScreenUI
import com.example.cumulora.features.savedweather.SavedWeatherScreenUI
import com.example.cumulora.features.settings.SettingsScreenUI
import com.example.cumulora.features.weather.WeatherScreenUI
import com.example.cumulora.ui.component.BackButton
import com.example.cumulora.utils.IS_FIRST_TIME_SK


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavSetup(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    val shared = SharedPreferenceHelper.getInstance()
    val isFirstTime = shared.getData(IS_FIRST_TIME_SK, true)
    val startingScreen = if (isFirstTime) ScreenRoutes.Onboarding else ScreenRoutes.Weather

    NavHost(navController = navController, startDestination = startingScreen) {

        composable<ScreenRoutes.Onboarding> {
            OnBoardingScreenUI {
                shared.saveData(IS_FIRST_TIME_SK, false)
                Log.i("TAG", "SAVE ATTEMPT = $isFirstTime ")
                navController.navigate(ScreenRoutes.Weather){
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }

        composable<ScreenRoutes.Weather> {
                WeatherScreenUI(
                    navController = navController
                ) {
                    navController.navigate(ScreenRoutes.Map)
                }
        }

        composable<ScreenRoutes.Alarm> {
            Scaffold(
                topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.alarms))
                }, navigationIcon = {
                    BackButton(navController)
                })
            }) { padding ->
                val cityName = it.toRoute<ScreenRoutes.Alarm>().cityName
                AlarmScreenUI(Modifier.padding(padding), snackbarHostState,cityName)
            }
        }

        composable<ScreenRoutes.SavedWeather> {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.saved_countries))
                }, navigationIcon = {
                    BackButton(navController)
                })
            }, floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate(
                        ScreenRoutes.Map
                    )
                }) {
                    Icon(imageVector = Icons.Default.AddLocationAlt, contentDescription = "Add")
                }
            }) { padding ->
                SavedWeatherScreenUI(Modifier.padding(padding), snackbarHostState, navController)
            }
        }

        composable<ScreenRoutes.Settings> {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text(stringResource(R.string.settings))
                }, navigationIcon = {
                    BackButton(navController)
                })
            }) { padding ->
                SettingsScreenUI(Modifier.padding(padding)) {
                    navController.navigate(ScreenRoutes.Map)
                }
            }
        }

        composable<ScreenRoutes.Map> {
            Scaffold(topBar = {
                TopAppBar(title = { Text(stringResource(R.string.choose_location)) }, navigationIcon = {
                    BackButton(navController)
                })
            }) { padding ->
                MapScreenUI(Modifier.padding(padding), navController)
            }
        }
    }
}