package com.example.cumulora.features.weather

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cumulora.R
import com.example.cumulora.core.factories.WeatherViewModelFactory
import com.example.cumulora.features.weather.component.CurrentTemperature
import com.example.cumulora.features.weather.component.WeatherDetailsSection
import com.example.cumulora.features.weather.responsestate.CombinedStateResponse
import com.example.cumulora.utils.repoInstance


//@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("NewApi")
@Composable
fun WeatherScreenUI(modifier: Modifier = Modifier, onMapNavigate: () -> Unit) {

    val ctx: Context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(repoInstance(ctx.applicationContext))
    )


    val combinedState by viewModel.combinedState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    val scrollProgress = remember(scrollState.value) {
        minOf(scrollState.value / 500f, 1f)
    }
    val startColor = Color.Black.copy(alpha = 0.3f)
    val endColor = Color.Black.copy(alpha = 0.92f)
    val currentBgColor = remember(scrollProgress) {
        lerp(startColor, endColor, scrollProgress)
    }

    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    val progress by animateLottieCompositionAsState(
        composition = lottie,
        restartOnPlay = true,
        speed = 1f,
        iterations = 1,
        isPlaying = true,
    )

    LaunchedEffect(Unit) {
        viewModel.refreshWeatherWithCurrentSettings()
    }
    when (combinedState) {
        is CombinedStateResponse.Failure -> {
            val ex = combinedState as CombinedStateResponse.Failure
            Log.e("TAG", "WeatherScreenUI: ${ex.error}")
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.failed_to_get_data))
            }
        }

        is CombinedStateResponse.Loading -> {
            Log.e("TAG", "WeatherScreenUI:")
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Transparent), contentAlignment = Alignment
                    .Center
            ) {
                LottieAnimation(
                    composition = lottie,
                    progress = { progress },

                    modifier = Modifier.size(120.dp)
                )
            }
        }

        is CombinedStateResponse.Success -> {
            val successData = combinedState as CombinedStateResponse.Success
            val weatherData = successData.weather.data
            val forecastData = successData.forecast.data
            val forecastFiveDays = successData.forecast.forecastFiveDays.takeLast(5)

            //TODO: Color black if knight, and cyan if day
            Box {
                Image(
                    painter = painterResource(id = R.drawable.clear_sky_nighttime2),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(Color.Transparent)
                ) {
                    CurrentTemperature(
                        weatherData.city,
                        weatherData.currentTemp,
                        weatherData.description,
                        weatherData.currentTime,
                        weatherData.currentDate,
                        weatherData.icon,
                        onMapNavigate
                    )
                    WeatherDetailsSection(weatherData, forecastData, forecastFiveDays,
                        bgColor = currentBgColor
                    )
                }
            }
        }
    }
}