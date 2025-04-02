package com.example.cumulora.features.map

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cumulora.R
import com.example.cumulora.core.factories.MapViewModelFactory
import com.example.cumulora.features.map.receiver.LocationReceiver
import com.example.cumulora.utils.isLocationEnabled
import com.example.cumulora.utils.isLocationPermissionGranted
import com.example.cumulora.utils.repoInstance
import com.example.cumulora.utils.requestLocationSettings
import com.example.cumulora.utils.waitForLocationUpdates
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import androidx.compose.runtime.DisposableEffect

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@Composable
fun MapScreenUI(modifier: Modifier = Modifier, navController: NavController) {
    val ctx = LocalContext.current

    var isTapped by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf(emptyList<AutocompletePrediction>()) }
    val places = Places.createClient(ctx)

    val viewModel: MapViewModel = viewModel(
        factory = MapViewModelFactory(
            repoInstance(ctx),
            places
        )
    )

    val latLng = viewModel.getLastLatLng()
    val markerState = rememberMarkerState(position = LatLng(latLng.first, latLng.second))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

    var result by remember { mutableStateOf<AutocompletePlace?>(null) }

    var isLocationServiceEnabled by remember { mutableStateOf(isLocationEnabled(ctx)) }

    DisposableEffect(ctx) {
        val receiver = LocationReceiver { isEnabled ->
            isLocationServiceEnabled = isEnabled
        }
        val intentFilter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)

        ctx.registerReceiver(receiver, intentFilter)

        onDispose {
            ctx.unregisterReceiver(receiver)
        }
    }

    LaunchedEffect(searchText) {
        predictions = viewModel.getAddressPredictions(inputString = searchText)
    }

    LaunchedEffect(markerState.position) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
        markerState.showInfoWindow()
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            if (!isLocationEnabled(ctx)) {
                requestLocationSettings(ctx as Activity) {}
            }
            waitForLocationUpdates(ctx) { latitude, longitude ->
                markerState.position = LatLng(latitude, longitude)
                cameraPositionState.position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
                markerState.showInfoWindow()
            }
        }) {
            Icon(imageVector = Icons.Default.MyLocation, contentDescription = "")
        }
    }) {

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = isLocationPermissionGranted(ctx) && isLocationServiceEnabled),
                onMapClick = { latLng ->
                    markerState.position = latLng
                    isTapped = true
                },
                uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = true)
            ) {
                Marker(
                    state = markerState,
                    title = stringResource(R.string.select_location),
                    visible = isTapped,
                    onInfoWindowClick = {
                        Log.i("TAG", "LAST LOCATION: ${markerState.position.latitude}, ${markerState.position.longitude}")
                        viewModel.saveLocation(
                            markerState.position.latitude,
                            markerState.position.longitude,
                        )
                        navController.popBackStack()
                    }
                )
            }
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
            }

            PlacesAutocompleteTextField(
                placeHolderText = stringResource(R.string.search_for_city),
                modifier = modifier.padding(top = 8.dp),
                searchText = searchText,
                predictions = predictions.map { prediction ->
                    AutocompletePlace(
                        placeId = prediction.placeId,
                        primaryText = prediction.getPrimaryText(null),
                        secondaryText = prediction.getSecondaryText(null)
                    )
                },
                onQueryChanged = { q -> searchText = q },
                onSelected = { autocompletePlace ->
                    result = autocompletePlace
                    predictions = emptyList()
                    viewModel.getLocationName(autocompletePlace, markerState)
                    isTapped = true
                },
                selectedPlace = result,
            )
        }
    }
}
