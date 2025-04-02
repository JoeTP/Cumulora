package com.example.cumulora.features.savedweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cumulora.R
import com.example.cumulora.core.factories.SavedWeatherViewModelFactory
import com.example.cumulora.data.responsestate.SavedWeatherStateResponse
import com.example.cumulora.features.savedweather.component.SavedWeatherCard
import com.example.cumulora.navigation.ScreenRoutes
import com.example.cumulora.ui.component.SwipeToDeleteContainer
import com.example.cumulora.utils.repoInstance

@Composable
fun SavedWeatherScreenUI(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: SavedWeatherViewModel = viewModel(
        factory = SavedWeatherViewModelFactory(repoInstance(context))
    )
    val savedWeatherListState by viewModel.savedWeatherList.collectAsStateWithLifecycle()

//    val msg by viewModel.message.collectAsStateWithLifecycle()
//    val snackBarHostState = remember { SnackbarHostState() }

//    LaunchedEffect(msg) {
//        if (msg.isNotBlank()) {
//            snackBarHostState.currentSnackbarData?.dismiss()
//            snackBarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
//        }
//    }

    Box(modifier = modifier.fillMaxSize()) {
        when (savedWeatherListState) {

            is SavedWeatherStateResponse.Loading -> {
                LoadingData()
            }

            is SavedWeatherStateResponse.Failure -> {}

            is SavedWeatherStateResponse.Success -> {
                val weatherList = (savedWeatherListState as SavedWeatherStateResponse.Success).data

                val units = viewModel.getUnits()


                if (weatherList.isEmpty()) {
                    NoData()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp), contentPadding =
                        PaddingValues(top = 16.dp, bottom = 86.dp), verticalArrangement =
                        Arrangement.spacedBy(16.dp)
                    ) {
                        items(weatherList, key = { it.cityName }) {
                            SwipeToDeleteContainer(
                                item = it,
                                onDelete = { viewModel.deleteSavedWeather(it) },
                                onRestore = { viewModel.restoreDeletedWeather(it) },
                                snackBarHostState = snackbarHostState,
                                snackBarString = it.cityName + context.getString(R.string.location_),
                                content = { savedWeather ->
                                    SavedWeatherCard(savedWeather, units) {
                                        val lat = savedWeather.weather!!.lat
                                        val lon = savedWeather.weather.lon
                                        viewModel.selectSavedWeather(lat, lon)
                                        navController.navigate(ScreenRoutes.Weather){
                                            popUpTo(navController.graph.id) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun NoData(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.no_saved_weather))
    }
}

@Composable
fun LoadingData() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
