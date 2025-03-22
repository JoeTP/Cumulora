package com.example.cumulora.features.map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cumulora.BuildConfig
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions
import com.google.android.libraries.places.compose.autocomplete.components.PlacesAutocompleteTextField
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.android.libraries.places.compose.autocomplete.models.toPlaceDetails
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MapScreenUI(modifier: Modifier = Modifier) {

    val singapore = LatLng(1.35, 103.87)
    val singaporeMarkerState = rememberMarkerState(position = singapore)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    val ctx = LocalContext.current

    Places.initializeWithNewPlacesApiEnabled(ctx, BuildConfig.GOOGLE_API_KEY)

    val searchTextFlow = remember { MutableStateFlow("") }
    val query by searchTextFlow.collectAsStateWithLifecycle()
    val geoHelper = GeocoderHelper(ctx)
    val result = remember { mutableStateOf<AutocompletePlace?>(null) }

    val prediction = remember { mutableStateOf(emptyList<AutocompletePrediction>()) }



    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = singaporeMarkerState,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
        PlacesAutocompleteTextField(
            modifier = modifier.padding(top = 20.dp),
            searchText = query,
            predictions = prediction.value.map { it.toPlaceDetails() },
            onQueryChanged = {
//                searchTextlll", "MapScreenUI: $it")
                searchTextFlow.value = it
                CoroutineScope(Dispatchers.IO).launch {
                    prediction.value = geoHelper.getAddressPredictions(inputString = it)
                }

            },
            onSelected = { autocompletePlace: AutocompletePlace ->
                result.value = autocompletePlace
                searchTextFlow.value = autocompletePlace.primaryText.toString()
                prediction.value = emptyList()
                Log.d("TAG", "MapScreenUI: ${result.value}")
            },
            selectedPlace = result.value,
        )
    }
}