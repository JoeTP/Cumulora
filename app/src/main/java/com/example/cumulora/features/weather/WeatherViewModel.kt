package com.example.cumulora.features.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import toFinalWeather

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val TAG = "TAG"

    private val _mutableWeather: MutableStateFlow<WeatherStateResponse> = MutableStateFlow(
        WeatherStateResponse.Loading)
    val weatherState: StateFlow<WeatherStateResponse> = _mutableWeather

    init {
        //pass parameters from shared preference
        getWeather(32.0, 21.0)
    }

    fun getWeather(lat: Double, lon: Double) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getWeather(lat, lon)
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

}

class WeatherViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

