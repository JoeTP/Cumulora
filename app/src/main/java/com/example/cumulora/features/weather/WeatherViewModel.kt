package com.example.cumulora.features.weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.repository.WeatherRepositoryImpl
import com.example.cumulora.features.settings.SettingsViewModel
import com.example.cumulora.features.weather.responsestate.CombinedStateResponse
import com.example.cumulora.features.weather.responsestate.ForecastStateResponse
import com.example.cumulora.features.weather.responsestate.WeatherStateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import toFinalWeather

@OptIn(FlowPreview::class)
class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val TAG = "TAG"

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
            val unit = repo.getCachedData("unit", "")
            val lang = repo.getCachedData("lang", "")
            getWeatherAndForecast(lat, lon, unit, lang)
            Log.d(TAG, "refreshWeatherWithCurrentSettings: ${unit} ${lang} ")
        } catch (e: Exception) {
            _mutableCombinedState.value = CombinedStateResponse.Failure("Refresh failed: ${e.message}")
        }
    }

    private fun getWeatherAndForecast(lat: Double, lon: Double, unit: String?, lang: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherDeferred =
                    async { repo.getWeather(lat, lon, unit, lang).catch { emit(null) }.first() }
                val forecastDeferred =
                    async { repo.getForecast(lat, lon, unit, lang).catch { emit(null) }.first() }

                val weather = weatherDeferred.await()
                val forecast = forecastDeferred.await()
                Log.d(TAG, "getWeatherAndForecast: ${weather?.main?.temp}")

                launch {
                    if (forecast != null) {
                        _mutableForecastFiveDays.value = forecast.forecastList
                            .distinctBy {
                                it.dtTxt.substring(0, 10)
                            }
                    }
                }

                if (weather != null && forecast != null) {
                    cachingLatLng(lat.toString(), lon.toString())
                    _mutableCombinedState.value = CombinedStateResponse.Success(
                        WeatherStateResponse.Success(weather.toFinalWeather()),
                        ForecastStateResponse.Success(forecast, _mutableForecastFiveDays.value)
                    )

                } else {
                    _mutableCombinedState.value = CombinedStateResponse.Failure("Error fetching data")
                }
            } catch (e: Exception) {
                _mutableCombinedState.value = CombinedStateResponse.Failure(e.message ?: "Unknown error")
            }
        }
    }

    private fun cachingLatLng(lat: String, lon: String) {
        repo.cacheData("lastLat", lat)
        repo.cacheData("lastLon", lon)
    }

     fun getLastLatLng(): Pair<Double, Double> {
        return Pair<Double, Double> (
            repo.getCachedData("lastLat", "0.0" ).toDouble(),
            repo.getCachedData("lastLon", "0.0").toDouble()
        )
    }


    /*    fun getWeather(lat: Double, lon: Double, unit: String?, lang: String?) =
            viewModelScope.launch(Dispatchers.IO) {
                val response = repo.getWeather(lat, lon, unit, lang)
                try {
                    response.catch {
                        //if response error
                        _mutableWeather.value = WeatherStateResponse.Failure(it.message.toString())
                    }.collect { weather ->
                        if (weather != null) {
                            _mutableWeather.value = WeatherStateResponse.Success(weather.toFinalWeather())
                        } else {
                            //if null
                            _mutableWeather.value = WeatherStateResponse.Failure("Error")
                        }
                    }
                } catch (e: Exception) {
                    //if network error
                    _mutableWeather.value = WeatherStateResponse.Failure(e.message.toString())
                }
            }

        fun getForecast(lat: Double, lon: Double, unit: String?, lang: String?) =
            viewModelScope.launch(Dispatchers.IO) {
                val response = repo.getForecast(lat, lon, unit, lang)
                try {
                    response.catch {
                        //if response error
                        _mutableForecast.value = ForecastStateResponse.Failure(it.message.toString())
                    }.collect { forecast ->
                        if (forecast != null) {
                            _mutableForecast.value = ForecastStateResponse.Success(forecast)
                        } else {
                            //if null
                            _mutableForecast.value = ForecastStateResponse.Failure("Error")
                        }
                    }
                } catch (e: Exception) {
                    //if network error
                    _mutableForecast.value = ForecastStateResponse.Failure(e.message.toString())
                }
            }*/

}


