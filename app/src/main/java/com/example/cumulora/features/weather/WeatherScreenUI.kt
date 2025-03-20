package com.example.cumulora.features.weather

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cumulora.features.weather.component.CurrentTemperature
import com.example.cumulora.features.weather.component.WeatherDetailsSection
import com.example.cumulora.features.weather.responsestate.ForecastStateResponse
import com.example.cumulora.features.weather.responsestate.WeatherStateResponse
import com.example.cumulora.utils.repoInstance

@Preview
@Composable
fun WeatherScreenUI(modifier: Modifier = Modifier) {

    val ctx: Context = LocalContext.current

    val viewModel = WeatherViewModel(repoInstance(ctx.applicationContext))

    val weather by viewModel.weatherState.collectAsStateWithLifecycle()
    val forecast by viewModel.forecastState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    when (weather) {
        is WeatherStateResponse.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("FAILED TO GET DATA")
            }
        }

        is WeatherStateResponse.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WeatherStateResponse.Success -> {
            when(forecast) {
                is ForecastStateResponse.Failure -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("FAILED TO GET FORECAST")
                    }
                }

                is ForecastStateResponse.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ForecastStateResponse.Success -> {
                    val weatherData = (weather as WeatherStateResponse.Success).data
                    val forecastData = (forecast as ForecastStateResponse.Success).data

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {

                        Surface(
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth(), color = Color.Red
                        ) {
                            CurrentTemperature(
                                weatherData.city, weatherData.currentTemp, weatherData.feelsLike, weatherData.description,
                                weatherData.currentDate, weatherData.currentTime
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
                        ) {
                            WeatherDetailsSection(weatherData, forecastData)
                        }
                    }
                }
            }
        }
    }
}