package com.example.cumulora.features.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.responsestate.SettingsState
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON
import com.example.cumulora.utils.LOCATION_TYPE
import com.example.cumulora.utils.UNITS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val repo: WeatherRepository) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    //notifier
//    private val _settingsChanged = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
//    val settingsChanged: SharedFlow<Unit> = _settingsChanged.asSharedFlow()


    init {
        loadInitialSettings()
    }

    private fun loadInitialSettings() {
        viewModelScope.launch {
            Log.i("SETTINGS", "LANG: ${repo.getCachedData(LANG, "")}")
            Log.i("SETTINGS", "LOCATION_TYPE: ${repo.getCachedData(LOCATION_TYPE, "")}")
            Log.i("SETTINGS", "UNITS: ${repo.getCachedData(UNITS, "")}")
            _settingsState.emit(
                SettingsState(
                    unit = repo.getCachedData(UNITS, ""),
                    locationType = repo.getCachedData(LOCATION_TYPE, ""),
                    lang = repo.getCachedData(LANG, "")
                )
            )
        }
    }

    fun changeUnit(unit: String) {
        Log.d("SETTINGS VIEWMODEL", "changeUnit: $unit")
        viewModelScope.launch {
            repo.cacheData(UNITS, unit)
            _settingsState.emit(settingsState.value.copy(unit = unit))
            repo.notifySettingsChanged()
        }
    }

    fun changeLocationType(locationType: String) {
        viewModelScope.launch {
            repo.cacheData(LOCATION_TYPE, locationType)
            _settingsState.emit(settingsState.value.copy(locationType = locationType))
            repo.notifySettingsChanged()
        }
    }

    fun useUserLocation(lat: String, lon: String) {
        viewModelScope.launch {
            repo.cacheData(LAST_LAT, lat)
            repo.cacheData(LAST_LON, lon)
        }
    }

    fun changeLang(lang: String) {
        viewModelScope.launch {
            repo.cacheData(LANG, lang)
            _settingsState.emit(settingsState.value.copy(lang = lang))
            repo.notifySettingsChanged()

        }
    }
}

