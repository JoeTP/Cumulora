package com.example.cumulora.features.weather

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cumulora.R
import com.example.cumulora.core.factories.WeatherViewModelFactory
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.features.weather.component.CurrentTemperature
import com.example.cumulora.features.weather.component.WeatherDetailsSection
import com.example.cumulora.data.responsestate.CombinedStateResponse
import com.example.cumulora.ui.component.MultiFab
import com.example.cumulora.ui.component.MyAppBar
import com.example.cumulora.ui.theme.CumuloraTheme
import com.example.cumulora.utils.getTempUnitSymbol
import com.example.cumulora.utils.getWindSpeedUnitSymbol
import com.example.cumulora.utils.repoInstance


@SuppressLint("NewApi")
@Composable
fun WeatherScreenUI(modifier: Modifier = Modifier, navController: NavController, onMapNavigate: () -> Unit) {

    val ctx: Context = LocalContext.current

    val viewModel: WeatherViewModel = viewModel(
        factory = WeatherViewModelFactory(repoInstance(ctx.applicationContext))
    )

    val windUnit = ctx.getWindSpeedUnitSymbol(viewModel.getUnit())
    val tempUnit = ctx.getTempUnitSymbol(viewModel.getUnit())

    val combinedState by viewModel.combinedState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
//
//    val scrollProgress = remember(scrollState.value) { minOf(scrollState.value / 500f, 1f) }
//
//    val startColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
//    val topBarStartColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
//    val endColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
//    val currentBgColor = remember(scrollProgress) {
//        lerp(startColor, endColor, scrollProgress)
//    }
//    val topBarBgColor = remember(scrollProgress) {
//        lerp(topBarStartColor, endColor, scrollProgress)
//    }

    val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_weather))

    val progress by animateLottieCompositionAsState(
        composition = lottie,
        restartOnPlay = true,
        speed = 1f,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )
    val lottieFailed by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))

    val progressLottieFailed by animateLottieCompositionAsState(
        composition = lottieFailed,
        restartOnPlay = true,
        speed = 1f,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )

    LaunchedEffect(Unit) {
        viewModel.refreshWeatherWithCurrentSettings()
    }

    when (combinedState) {
        is CombinedStateResponse.Failure -> {
            val ex = combinedState as CombinedStateResponse.Failure
            Log.e("TAG", "WeatherScreenUI: ${ex.error}")
            Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment =   Alignment.CenterHorizontally) {
                LottieAnimation(
                    composition = lottieFailed,
                    progress = { progressLottieFailed },
                    modifier = Modifier.size(120.dp)
                )
                Text(ex.error)
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


            DisplayWeatherScreen(
                modifier = modifier,
                navController = navController,
                scrollState = scrollState,
                weatherData = weatherData,
                forecastData = forecastData,
                forecastFiveDays = forecastFiveDays,
//                currentBgColor = currentBgColor,
//                topBarBgColor = topBarBgColor,
                onMapNavigate = onMapNavigate,
                windUnit  = windUnit ,
                tempUnit  = tempUnit
            )
        }
    }
}
@SuppressLint("NewApi")
@Composable
fun DisplayWeatherScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    scrollState: ScrollState,
    weatherData: WeatherEntity,
    forecastData: ForecastResponse,
    forecastFiveDays: List<Forecast>,
//    currentBgColor: Color,
//    topBarBgColor: Color,
    windUnit: String,
    tempUnit: String,
    onMapNavigate: () -> Unit
) {
    val scrollProgress by remember(scrollState.value) {
        derivedStateOf {
            minOf(scrollState.value / 500f, 1f)
        }
    }

    val titleAlpha by animateFloatAsState(
        targetValue = if (scrollProgress < 0.8f) 0f else scrollProgress,
        label = "titleAlpha"
    )

    val currentTemp = weatherData.currentTemp.toInt().toString() + " "+ tempUnit
    val cityName = weatherData.city
    val isDay = weatherData.icon.contains("d")

    CumuloraTheme(forceDayNightTheme = isDay) {
        val surfaceColor = MaterialTheme.colorScheme.surface

        val topBarColor = remember(scrollProgress) {
            surfaceColor.copy(alpha = scrollProgress * 0.9f)
        }

        val detailsBgAlpha = remember(scrollProgress) {
            0.4f + (scrollProgress * (0.9f - 0.15f))
        }

        Scaffold(
            topBar = {
                MyAppBar(
                    navController = navController,
                    bgColor = topBarColor,
                    cityName = cityName,
                    currentTemp = currentTemp,
                    titleAlpha = titleAlpha
                )
            },
            floatingActionButton = {
                MultiFab(navController, cityName)
            }
        ) { padding ->
            Box {
                BackgroundImage(if (isDay) R.drawable.fuji_day else R.drawable.fuji_knight)
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(Color.Transparent)
                ) {


                    CurrentTemperature(
                        cityName = weatherData.city,
                        temperature = weatherData.currentTemp,
                        description = weatherData.description,
                        time = weatherData.currentTime,
                        date = weatherData.currentDate,
                        icon = weatherData.icon,
                        feelsLike = weatherData.feelsLike.toInt().toString(),
                        tempUnit = tempUnit,
                        onMapNavigate = onMapNavigate
                    )
                    WeatherDetailsSection(
                        weatherData,
                        forecastData,
                        forecastFiveDays,
                        tempUnit,
                        windUnit,
                        bgColor = surfaceColor.copy(alpha = detailsBgAlpha)
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundImage(imageId: Int) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "",
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .fillMaxSize()
    )
}
