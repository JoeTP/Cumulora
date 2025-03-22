package com.example.cumulora.features.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.weather.responsestate.CombinedStateResponse
import com.example.cumulora.features.weather.responsestate.ForecastStateResponse
import com.example.cumulora.features.weather.responsestate.WeatherStateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import toFinalWeather

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val TAG = "TAG"

    /*
        private val _mutableWeather: MutableStateFlow<WeatherStateResponse> =
            MutableStateFlow(WeatherStateResponse.Loading)
        val weatherState: StateFlow<WeatherStateResponse> = _mutableWeather

        private val _mutableForecast: MutableStateFlow<ForecastStateResponse> =
            MutableStateFlow(ForecastStateResponse.Loading)
        val forecastState: StateFlow<ForecastStateResponse> = _mutableForecast
    */

    private val _mutableCombinedState: MutableStateFlow<CombinedStateResponse> =
        MutableStateFlow(CombinedStateResponse.Loading)
    val combinedState: StateFlow<CombinedStateResponse> = _mutableCombinedState.asStateFlow()

    private val _mutableForecastFiveDays = MutableStateFlow(listOf<Forecast>())
    val forecastFiveDays: StateFlow<List<Forecast>> = _mutableForecastFiveDays.asStateFlow()


    init {
        //pass parameters from shared preference
        getWeatherAndForecast(0.0, 0.0, null, null)
    }


     fun getWeatherAndForecast(lat: Double, lon: Double, unit: String?, lang: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weatherDeferred =
                    async { repo.getWeather(lat, lon, unit, lang).catch { emit(null) }.first() }
                val forecastDeferred =
                    async { repo.getForecast(lat, lon, unit, lang).catch { emit(null) }.first() }

                val weather = weatherDeferred.await()
                val forecast = forecastDeferred.await()

                launch {
                    if (forecast != null) {
                        _mutableForecastFiveDays.value = forecast.forecastList
                            .distinctBy {
                                it.dtTxt.substring(0, 10)
                            }
                    }
                }

                if (weather != null && forecast != null) {
                    _mutableCombinedState.value = CombinedStateResponse.Success(
                        WeatherStateResponse.Success(weather.toFinalWeather()),
                        ForecastStateResponse.Success(forecast, forecastFiveDays.value)
                    )

                } else {
                    _mutableCombinedState.value = CombinedStateResponse.Failure("Error fetching data")
                }
            } catch (e: Exception) {
                _mutableCombinedState.value = CombinedStateResponse.Failure(e.message ?: "Unknown error")
            }
        }
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

class WeatherViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

