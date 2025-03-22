package com.example.cumulora.features.map

import android.content.Context
import android.location.Geocoder
import android.location.Location
import androidx.compose.runtime.MutableState
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeocoderHelper(ctx: Context) {

    val client = Places.createClient(ctx)
    lateinit var coordinates: MutableState<Location>
    suspend fun getAddressPredictions(
        sessionToken: AutocompleteSessionToken = AutocompleteSessionToken.newInstance(),
        inputString: String,
        location: LatLng? = null
    ) = suspendCoroutine<List<AutocompletePrediction>> {

        client.findAutocompletePredictions(
            FindAutocompletePredictionsRequest
                .builder()
                .setOrigin(location)
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .setSessionToken(sessionToken)
                .setQuery(inputString)
                .build()
        ).addOnCompleteListener { completedTask ->
            if (completedTask.exception != null) {
                it.resume(listOf())
            } else {
                it.resume(completedTask.result.autocompletePredictions)
            }
        }
    }

    private fun fetchCoordinates(addressString: String) {
        val geocoder = Geocoder(ctx, Locale.getDefault())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Perform reverse geocoding
                val addresses = geocoder.getFromLocationName(addressString, 1)
                if (addresses?.isNotEmpty() == true) {
                    val latitude = addresses[0].latitude
                    val longitude = addresses[0].longitude
                    // Update LiveData or State with the coordinates
                    coordinates.value = LatLng(latitude, longitude)
                } else {
                    // Handle case where no coordinates are found
                    coordinates.value = null
                }
            } catch (e: Exception) {
                coordinates.value = null
            }
        }
    }

}