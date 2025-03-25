package com.example.cumulora.features.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.local.SavedWeather
import com.example.cumulora.data.repository.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.compose.autocomplete.models.AutocompletePlace
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MapViewModel(private val repo: WeatherRepository, private val placesClient: PlacesClient) :
    ViewModel() {

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

