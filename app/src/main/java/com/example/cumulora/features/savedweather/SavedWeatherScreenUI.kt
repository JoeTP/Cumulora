package com.example.cumulora.features.savedweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.features.savedweather.component.SavedWeatherCard
import com.example.cumulora.utils.repoInstance


@Composable
fun SavedWeatherScreenUI(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val viewModel: SavedWeatherViewModel =
        viewModel(factory = SavedWeatherViewModelFactory(repoInstance(context)))

    val savedWeatherListState by viewModel.savedWeatherList.collectAsStateWithLifecycle()

    val msg by viewModel.message.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }


    when (savedWeatherListState) {
        is SavedWeatherStateResponse.Loading -> {
            LoadingData()
        }

        is SavedWeatherStateResponse.Failure -> {
            LaunchedEffect(msg) {
                if (msg.isNotBlank())
                    snackBarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            }
        }

        is SavedWeatherStateResponse.Success -> {
            val savedWeatherList = (savedWeatherListState as SavedWeatherStateResponse.Success).data
            if (savedWeatherList.isEmpty()) {
                NoData(modifier)
            } else {
                val localList = (savedWeatherListState as SavedWeatherStateResponse.Success).data
                LazyColumn(
                    modifier = modifier, contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(localList) { index, item ->
                        SavedWeatherCard(item)
                    }
                }
            }
        }
    }
}

@Composable
fun NoData(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No Saved Weather")
    }
}

@Composable
fun LoadingData() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}