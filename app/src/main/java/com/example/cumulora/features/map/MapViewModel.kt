package com.example.cumulora.features.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.savedweather.model.SavedWeather
import com.example.cumulora.utils.DEFAULT_UNITS
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON
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
import toFinalWeather
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MapViewModel(private val repo: WeatherRepository, private val placesClient: PlacesClient) :
    ViewModel() {

    fun getLocationName(
        autocompletePlace: AutocompletePlace,
        markerState: MarkerState
    ) = viewModelScope.launch {
        try {
            val placeFields = listOf(Place.Field.LOCATION)
            val request = FetchPlaceRequest.builder(autocompletePlace.placeId, placeFields).build()

            val place = suspendCoroutine { continuation ->
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response ->
                        continuation.resume(response.place)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TAG", "Error fetching place details: ${exception.message}")
                        continuation.resume(null)
                    }
            }

            place?.location?.let { location ->
                markerState.position = LatLng(location.latitude, location.longitude)
                Log.d("TAG", "Selected Place: ${autocompletePlace.primaryText}, Coordinates: $location")
            }
        } catch (e: Exception) {
            Log.e("TAG", "Error in getLocationName: ${e.message}")
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
        cacheLastLatLon(lat.toString(), lon.toString())
//        val unit = repo.getCachedData(UNITS, DEFAULT_UNITS)
        val lang = repo.getCachedData(LANG, "")
        //? Temp will be called with metric units by default
        val weatherDeferred =
            async { repo.getWeather(lat, lon, DEFAULT_UNITS, lang).catch { emit(null) }.first() }
//        val forecastDeferred = async { repo.getForecast(lat, lon, unit, lang).catch { emit(null) }.first() }
        val weather = weatherDeferred.await()
        weather?.dt = System.currentTimeMillis()
//        val forecast = forecastDeferred.await()
        repo.saveWeather(SavedWeather(weather?.name ?: "Unknown Location", weather?.toFinalWeather()))
    }

    fun cacheLastLatLon(lat: String, lon: String) {
        repo.cacheData(LAST_LAT, lat)
        repo.cacheData(LAST_LON, lon)
    }

    fun getLastLatLng(): Pair<Double, Double> {
        return Pair(
            repo.getCachedData(LAST_LAT, "0.0").toDouble(),
            repo.getCachedData(LAST_LON, "0.0").toDouble()
        )
    }


}

