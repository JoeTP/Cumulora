package com.example.cumulora.features.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.repository.WeatherRepositoryImpl
import com.example.cumulora.features.weather.model.HomeEntity
import com.example.cumulora.data.responsestate.CombinedStateResponse
import com.example.cumulora.data.responsestate.ForecastStateResponse
import com.example.cumulora.data.responsestate.WeatherStateResponse
import com.example.cumulora.utils.DEFAULT_UNITS
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON
import com.example.cumulora.utils.UNITS
import com.example.cumulora.utils.isInternetAvailable
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import toFinalWeather

@OptIn(FlowPreview::class)
class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val TAG = "WeatherViewModel"

    private val _mutableCombinedState: MutableStateFlow<CombinedStateResponse> =
        MutableStateFlow(CombinedStateResponse.Loading)
    val combinedState: StateFlow<CombinedStateResponse> = _mutableCombinedState.asStateFlow()

    private val _mutableForecastFiveDays = MutableStateFlow(listOf<Forecast>())


    init {
        viewModelScope.launch {
            WeatherRepositoryImpl.settingsChanges
                .debounce(500)
                .collect {
                    refreshWeatherWithCurrentSettings()
                }
        }
    }

    fun refreshWeatherWithCurrentSettings() {
        _mutableCombinedState.value = CombinedStateResponse.Loading
        try {
            val (lat, lon) = getLastLatLng()
            val unit = repo.getCachedData(UNITS, DEFAULT_UNITS)
            val lang = repo.getCachedData(LANG, "")
            if(isInternetAvailable()) {
                getWeatherAndForecast(lat, lon, unit, lang)
            }else{
                getCachedHome()
            }
            Log.d(TAG, "refreshWeatherWithCurrentSettings: ${unit} - ${lang} - ${lat} - ${lon}")
        } catch (e: Exception) {
            _mutableCombinedState.value = CombinedStateResponse.Failure("Refresh failed: ${e.message}")
        }
    }

    private fun getWeatherAndForecast(lat: Double, lon: Double, unit: String?, lang: String?) {
        viewModelScope.launch {
            _mutableCombinedState.value = CombinedStateResponse.Loading

            try {
                val weather = repo.getWeather(lat, lon, unit, lang).catch { emit(null) }.firstOrNull()

                val forecast = repo.getForecast(lat, lon, unit, lang).catch { emit(null) }.firstOrNull()

                Log.d(TAG, "getWeatherAndForecast: ${weather?.name}")

                if (forecast != null) {
                    _mutableForecastFiveDays.value = forecast.forecastList.distinctBy { it.dtTxt.substring(0, 10) }
                }

                when {
                    weather != null && forecast != null -> {
                        cachingLatLng(lat.toString(), lon.toString())
                        _mutableCombinedState.value = CombinedStateResponse.Success(
                            WeatherStateResponse.Success(weather.toFinalWeather()),
                            ForecastStateResponse.Success(forecast, _mutableForecastFiveDays.value)
                        )
                        cacheHome(HomeEntity(0, weather.toFinalWeather(), forecast))
                    }

                    weather == null && forecast == null -> {
                        _mutableCombinedState.value = CombinedStateResponse.Failure("No network connection")
                    }

                    else -> {
                        _mutableCombinedState.value = CombinedStateResponse.Failure("Partial data received")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "getWeatherAndForecast error", e)
                _mutableCombinedState.value = CombinedStateResponse.Failure(
                    e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun cachingLatLng(lat: String, lon: String) {
        repo.cacheData(LAST_LAT, lat)
        repo.cacheData(LAST_LON, lon)
    }

    fun getLastLatLng(): Pair<Double, Double> {
        //TODO: CALL THE GET MY LOCATION FN HERE AND GIVE THEM TO THE DEFAULT VALUES "0.0"
        return Pair(
            repo.getCachedData(LAST_LAT, "0.0").toDouble(),
            repo.getCachedData(LAST_LON, "0.0").toDouble()
        )
    }

    fun getUnit(): String = repo.getCachedData(UNITS, DEFAULT_UNITS)

    fun cacheHome(homeEntity: HomeEntity) = viewModelScope.launch {
        repo.cacheHomeCachedWeather(homeEntity)
    }

    fun getCachedHome() = viewModelScope.launch {
        try {
            repo.getHomeCachedWeather().catch { }.collect {
                val forecast = it.forecast
                _mutableForecastFiveDays.value = forecast.forecastList.distinctBy { it.dtTxt.substring(0, 10) }
                _mutableCombinedState.value = CombinedStateResponse.Success(
                    WeatherStateResponse.Success(it.weather),
                    ForecastStateResponse.Success(it.forecast, _mutableForecastFiveDays.value)
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "getCachedHome: ${e.message}")
        }
    }
}
