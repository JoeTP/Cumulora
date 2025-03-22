package com.example.cumulora.features.map

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeocoderViewModel(private val ctx: Context): ViewModel() {


    fun getLocationName(autocompletePlace: AutocompletePlace, markerState: MarkerState){

        val placeFields = listOf(Place.Field.LOCATION)
        val request = FetchPlaceRequest.builder(autocompletePlace.placeId, placeFields).build()

        Places.createClient(ctx).fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                val newPosition =
                    place.location ?: LatLng(0.0, 0.0)
                markerState.position = newPosition
                Log.d(
                    "TAG",
                    "Selected Place: ${autocompletePlace.primaryText}, Coordinates: $newPosition"
                )
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error fetching place details: ${exception.message}")
            }
    }

    suspend fun getAddressPredictions(inputString: String): List<AutocompletePrediction> {
        val client = Places.createClient(ctx)

        return suspendCoroutine { continuation ->
            val token = AutocompleteSessionToken.newInstance()
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(inputString)
                .setSessionToken(token)
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .build()

            client.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    continuation.resume(response.autocompletePredictions)
                }
                .addOnFailureListener { exception ->
                    Log.e("GeocoderHelper", "Error fetching predictions: ${exception.message}")
                    continuation.resume(emptyList())
                }
        }
    }



}

class GeocoderViewModelFactory(private val ctx: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeocoderViewModel::class.java)) {
            return GeocoderViewModel(ctx) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}