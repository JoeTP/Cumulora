package com.example.cumulora.features.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.AppInitializer
import com.example.cumulora.data.local.SavedWeather
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.example.cumulora.data.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GeocoderViewModel(private val repo: WeatherRepository, private val placesClient: PlacesClient) :
    ViewModel() {

//    private val sharedPref: SharedPreferenceHelper =
//        SharedPreferenceHelper.getInstance(AppInitializer.getAppContext())

    fun getLocationName(autocompletePlace: AutocompletePlace, markerState: MarkerState) {

        val placeFields = listOf(Place.Field.LOCATION)
        val request = FetchPlaceRequest.builder(autocompletePlace.placeId, placeFields).build()

        placesClient.fetchPlace(request).addOnSuccessListener { response ->
                val place = response.place
                val newPosition = place.location ?: LatLng(0.0, 0.0)
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


//    fun getLocationNameFromMarker(
//        context: Context,
//        markerState: MarkerState,
//        placesClient: PlacesClient,
//        onSuccess: (String) -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        // Check permissions first
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
//            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            val latLng = markerState.position
//            val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//
//            placesClient.findCurrentPlace(FindCurrentPlaceRequest.newInstance(placeFields))
//                .addOnSuccessListener { response ->
//                    val place = response.placeLikelihoods.firstOrNull()?.place
//                    place?.let {
//                        val preciseLocation = it.latLng ?: latLng
//                        markerState.position = preciseLocation
//                        val locationName = it.name ?: it.address ?: "Unknown location"
//                        onSuccess(locationName)
//                    } ?: onFailure(Exception("No place found"))
//                }
//                .addOnFailureListener { exception ->
//                    onFailure(exception)
//                }
//        } else {
//            onFailure(SecurityException("Location permission not granted"))
//        }
//    }

    suspend fun getAddressPredictions(inputString: String): List<AutocompletePrediction> {

        return suspendCoroutine { continuation ->
            val token = AutocompleteSessionToken.newInstance()
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(inputString)
                .setSessionToken(token)
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .build()

            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    continuation.resume(response.autocompletePredictions)
                }
                .addOnFailureListener { exception ->
                    Log.e("GeocoderHelper", "Error fetching predictions: ${exception.message}")
                    continuation.resume(emptyList())
                }
        }
    }

    fun saveLocation(lat: Double, lon: Double) = viewModelScope.launch {
            val unit = repo.getCachedData("unit", "metric")
            val lang = repo.getCachedData("lang", "en")
            val weatherDeferred =
                async { repo.getWeather(lat, lon, unit, lang).catch { emit(null) }.first() }
            val forecastDeferred =
                async { repo.getForecast(lat, lon, unit, lang).catch { emit(null) }.first() }

            val weather = weatherDeferred.await()
            val forecast = forecastDeferred.await()

            repo.saveWeather(SavedWeather(forecast?.city?.name ?: "", weather, forecast))
        }

    fun cacheLastLatLon(lat: String, lon: String) {
        repo.cacheData("lastLat", lat)
        repo.cacheData("lastLon", lon)
    }


}

class GeocoderViewModelFactory(private val repo: WeatherRepository, private val placesClient: PlacesClient) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GeocoderViewModel::class.java)) {
            return GeocoderViewModel(repo, placesClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}