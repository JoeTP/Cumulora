package com.example.cumulora.features.onboard

import android.Manifest
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.OnboardViewModelFactory
import com.example.cumulora.features.settings.TAG
import com.example.cumulora.utils.isLocationEnabled
import com.example.cumulora.utils.isLocationPermissionGranted
import com.example.cumulora.utils.repoInstance
import com.example.cumulora.utils.requestLocationSettings
import com.example.cumulora.utils.waitForLocationUpdates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreenUI(onNavigateToHome: () -> Unit) {
    val context = LocalContext.current
    val viewModel: OnboardViewModel = viewModel(
        factory = OnboardViewModelFactory(repoInstance(context))
    )

    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        handlePermissionResult(isGranted, context, viewModel, scope) {
            isLoading = true
            scope.launch {
                delay(1000) // Simulate loading
                onNavigateToHome()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AppLogoSection()
        Spacer(Modifier.height(16.dp))
        StartButton(
            onClick = {
                handleStartClick(
                    context = context,
                    locationPermissionLauncher = locationPermissionLauncher,
                    viewModel = viewModel,
                    scope = scope,
                    onSuccess = {
                        isLoading = true
                        scope.launch {
                            delay(1000)
                            onNavigateToHome()
                        }
                    }
                )
            }
        )
    }

    if (isLoading) {
        FullScreenLoading()
    }
}

@Composable
private fun AppLogoSection() {


    Column(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth()
            .background(Color.Red)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.landing_image),
            contentScale = ContentScale.FillHeight,
            contentDescription = ""
        )
    }
}

@Composable
private fun StartButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 32.dp)
    ) {
        Text(stringResource(R.string.start))
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

private fun handleStartClick(
    context: Context,
    locationPermissionLauncher: ActivityResultLauncher<String>,
    viewModel: OnboardViewModel,
    scope: CoroutineScope,
    onSuccess: () -> Unit
) {
    when {
        !isLocationPermissionGranted(context) -> {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        !isLocationEnabled(context) -> {
            (context as? Activity)?.let { activity ->
                requestLocationSettings(activity) { isLocationEnabled ->
                    if (isLocationEnabled) {
                        fetchAndCacheLocation(context, viewModel, scope, onSuccess)
                    }
                }
            }
        }

        else -> {
            fetchAndCacheLocation(context, viewModel, scope, onSuccess)
        }
    }
}

private fun handlePermissionResult(
    isGranted: Boolean,
    context: Context,
    viewModel: OnboardViewModel,
    scope: CoroutineScope,
    onSuccess: () -> Unit
) {
    if (isGranted) {
        if (!isLocationEnabled(context)) {
            (context as? Activity)?.let { activity ->
                requestLocationSettings(activity) { isLocationEnabled ->
                    if (isLocationEnabled) {
                        fetchAndCacheLocation(context, viewModel, scope, onSuccess)
                    }
                }
            }
        } else {
            fetchAndCacheLocation(context, viewModel, scope, onSuccess)
        }
    }
}

private fun fetchAndCacheLocation(
    context: Context,
    viewModel: OnboardViewModel,
    scope: CoroutineScope,
    onSuccess: () -> Unit
) {
    waitForLocationUpdates(context) { latitude, longitude ->
        Log.d(TAG, "Location received: $latitude, $longitude")
        viewModel.cacheUserLatLon(latitude, longitude)
        scope.launch {
            onSuccess()
        }
    }
}