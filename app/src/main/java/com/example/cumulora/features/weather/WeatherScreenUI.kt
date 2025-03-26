package com.example.cumulora.features.weather

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.WeatherViewModelFactory
import com.example.cumulora.features.weather.component.CurrentTemperature
import com.example.cumulora.features.weather.component.WeatherDetailsSection
import com.example.cumulora.features.weather.responsestate.CombinedStateResponse
import com.example.cumulora.utils.repoInstance


//@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreenUI(modifier: Modifier = Modifier, onMapNavigate: () -> Unit) {

    val ctx: Context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(repoInstance(ctx.applicationContext))
    )


    val combinedState by viewModel.combinedState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.refreshWeatherWithCurrentSettings()
    }

    when (combinedState) {
        is CombinedStateResponse.Failure -> {
            val ex = combinedState as CombinedStateResponse.Failure
            Log.e("TAG", "WeatherScreenUI: ${ex.error}")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.failed_to_get_data))
            }
        }

        is CombinedStateResponse.Loading -> {
            Log.e("TAG", "WeatherScreenUI:")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CombinedStateResponse.Success -> {
            val successData = combinedState as CombinedStateResponse.Success

            val weatherData = successData.weather.data
            val forecastData = successData.forecast.data
            val forecastFiveDays = successData.forecast.forecastFiveDays.takeLast(5)

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                CurrentTemperature(
                    weatherData.city,
                    weatherData.currentTemp,
                    weatherData.feelsLike,
                    weatherData.description,
                    weatherData.currentTime,
                    weatherData.icon,
                    onMapNavigate
                )
                WeatherDetailsSection(weatherData, forecastData, forecastFiveDays)
            }
        }
    }
}