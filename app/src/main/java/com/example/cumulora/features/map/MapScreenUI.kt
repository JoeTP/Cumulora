package com.example.cumulora.features.map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.toSpannable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.utils.repoInstance
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.concatWith

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MapScreenUI(modifier: Modifier = Modifier) {

    val ctx = LocalContext.current
    val viewModel : GeocoderViewModel = viewModel(factory = GeocoderViewModelFactory(ctx, repoInstance(ctx)))

//    val singapore = LatLng(1.35, 103.87)
    val markerState = rememberMarkerState(position = LatLng(1.35, 103.87))
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

    val latlng = viewModel.stateFlow.collectAsStateWithLifecycle()
    //TODO: DO THE RESPONSE STATE FOR THE GEOCODER

    var searchText by remember { mutableStateOf("") }

    var predictions by remember { mutableStateOf(emptyList<AutocompletePrediction>()) }


    val result = remember { mutableStateOf<AutocompletePlace?>(null) }

    LaunchedEffect(searchText) {
        predictions = viewModel.getAddressPredictions(inputString = searchText)
    }

    LaunchedEffect(markerState.position) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(markerState.position, 10f)
    }

    Box (modifier = Modifier.fillMaxSize()){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(mapType = MapType.HYBRID)

        ) {
            Marker(
                state = markerState,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
        PlacesAutocompleteTextField(
            modifier = modifier.padding(top = 20.dp),
            searchText = searchText,
            predictions = predictions.map { prediction ->
                AutocompletePlace(
                    placeId = prediction.placeId,
                    primaryText = prediction.getPrimaryText(null).toSpannable(),
                    secondaryText = prediction.getSecondaryText(null).toSpannable()
                )
            },
            onQueryChanged = { q -> searchText = q },
            onSelected = { autocompletePlace ->
                result.value = autocompletePlace
                predictions = emptyList()
                markerState.position = LatLng(10.35, 103.87)
                Log.d("TAG", "Selected Place: ${autocompletePlace.primaryText}")
            },
            selectedPlace = result.value,
        )

    }
}