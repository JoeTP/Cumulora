package com.example.cumulora

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.Weather
import com.example.cumulora.data.models.WeatherResponse
import com.example.cumulora.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityVM(private val repo: WeatherRepository) : ViewModel() {

    private val TAG = "MainActivityVM"

    private val _mutableWeather: MutableLiveData<WeatherResponse?> = MutableLiveData<WeatherResponse?>()
    val weather: LiveData<WeatherResponse?> = _mutableWeather

    fun getWeather(lat: Double, lon: Double) = viewModelScope.launch(Dispatchers.IO) {
        val response = repo.getWeather(lat, lon)
        try {
            if(response != null) {
                _mutableWeather.postValue(response)
                Log.d(TAG, "getWeather: SUCCESS ${response}")
            }else{
                Log.e(TAG, "getWeather: EMPTY LIST")
            }
        } catch (e: Exception) {
                Log.e(TAG, "getWeather: ERROR", e)
        }
    }
}

class MainActivityVMFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            return MainActivityVM(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


