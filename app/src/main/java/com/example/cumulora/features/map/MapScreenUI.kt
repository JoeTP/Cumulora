package com.example.cumulora.features.map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.MapViewModelFactory
import com.example.cumulora.utils.repoInstance
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@Composable
fun MapScreenUI(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current

    //TODO: Call shared preference with the last saved location and set it as the marker position
    val markerState = rememberMarkerState(position = LatLng(1.35, 103.87))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

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

    var result by remember { mutableStateOf<AutocompletePlace?>(null) }

    LaunchedEffect(searchText) {
        predictions = viewModel.getAddressPredictions(inputString = searchText)
    }

    LaunchedEffect(markerState.position) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
        markerState.showInfoWindow()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                markerState.position = latLng
                isTapped = true
            }
        ) {
            Marker(
                state = markerState,
                title = stringResource(R.string.select_location),
                snippet = result?.secondaryText.toString(),
                visible = isTapped,
                onInfoWindowClick = {
                    //TODO: Add to favorites HERE and show snack bar "CITY NAME added to favorites"
                    Log.i(
                        "TAG",
                        "ADD TO FAV: ${markerState.position.latitude}, ${markerState.position.longitude}"
                    )

                    //TODO: This is not the place!!!!
                    viewModel.saveLocation(
                        markerState.position.latitude,
                        markerState.position.longitude,
                    )

                    viewModel.cacheLastLatLon(
                        markerState.position.latitude.toString(),
                        markerState.position.longitude.toString()
                    )
                }
            )
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                //TODO: CHANGE COLOR
                .background(Color.LightGray)
        ) {
        }
        PlacesAutocompleteTextField(
            placeHolderText = stringResource(R.string.search_for_city),
            modifier = modifier
                .padding(top = 8.dp),
            searchText = searchText,
            predictions = predictions.map { prediction ->
                AutocompletePlace(
                    placeId = prediction.placeId,
                    primaryText = prediction.getPrimaryText(null),
                    secondaryText = prediction.getSecondaryText(null)
                )
            },
            onQueryChanged = { q ->
                searchText = q
            },
            onSelected = { autocompletePlace ->
                result = autocompletePlace
                predictions = emptyList()
                isTapped = true
                //TODO: SAVE THE LATLNG TO SHARED PREF SO U CAN CALL IT BACK IN THE HOME SCREEN
                viewModel.getLocationName(autocompletePlace, markerState)
            },
            selectedPlace = result,
        )

    }
}