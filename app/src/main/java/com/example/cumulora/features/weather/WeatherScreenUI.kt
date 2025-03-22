package com.example.cumulora.features.weather

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cumulora.features.weather.component.CurrentTemperature
import com.example.cumulora.features.weather.component.WeatherDetailsSection
import com.example.cumulora.features.weather.responsestate.CombinedStateResponse
import com.example.cumulora.utils.repoInstance

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreenUI(modifier: Modifier = Modifier, onMapNavigate: () -> Unit) {

    val ctx: Context = LocalContext.current

    val viewModel = WeatherViewModel(repoInstance(ctx.applicationContext))

    val combinedState by viewModel.combinedState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    when (combinedState) {
        is CombinedStateResponse.Failure -> {
            val ex = combinedState as CombinedStateResponse.Failure
            Log.e("TAG", "WeatherScreenUI: ${ex.error}")
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("FAILED TO GET DATA")
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
                    weatherData.city, weatherData.currentTemp, weatherData.feelsLike, weatherData.description,
                    weatherData.currentDate, weatherData.currentTime, weatherData.icon, onMapNavigate
                )

                WeatherDetailsSection(weatherData, forecastData, forecastFiveDays)

            }
        }
    }
}