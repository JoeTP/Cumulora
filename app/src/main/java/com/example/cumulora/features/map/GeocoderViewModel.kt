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
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeocoderViewModel(private val ctx: Context, val repo: WeatherRepository): ViewModel() {

    private val _mutableStateFlow = MutableStateFlow<GeocoderResponse?>(null)
    val stateFlow = _mutableStateFlow.asStateFlow()

    val client = Places.createClient(ctx)
    lateinit var coordinates: MutableState<LatLng?>

    suspend fun getAddressPredictions(inputString: String): List<AutocompletePrediction> {

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

    suspend fun hamada(query: String){

        repo.getGeocoder(query).catch {

        }.collect {
            _mutableStateFlow.value = it
        }
    }

//    suspend fun

    fun fetchCoordinates(addressString: String) {
        val geocoder = Geocoder(ctx, Locale.getDefault())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val addresses = geocoder.getFromLocationName(addressString, 1)
                if (addresses?.isNotEmpty() == true) {
                    val latitude = addresses[0].latitude
                    val longitude = addresses[0].longitude
                    coordinates.value = LatLng(latitude, longitude)
                } else {
                    coordinates.value = null
                }
            } catch (e: Exception) {
                coordinates.value = null
            }
        }
    }

}

class GeocoderViewModelFactory(private val ctx: Context, private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeocoderViewModel::class.java)) {
            return GeocoderViewModel(ctx, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}