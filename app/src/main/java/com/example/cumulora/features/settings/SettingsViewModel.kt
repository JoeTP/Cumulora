package com.example.cumulora.features.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LOCATION_TYPE
import com.example.cumulora.utils.UNITS
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
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
            Log.i("SETTINGS", "UNITS: ${repo.getCachedData(UNITS, "")}")
            Log.i("SETTINGS", "LOCATION_TYPE: ${repo.getCachedData(LOCATION_TYPE, "")}")
            Log.i("SETTINGS", "LANG: ${repo.getCachedData(LANG, "")}")
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

    fun changeLang(lang: String) {
        viewModelScope.launch {
            repo.cacheData(LANG, lang)
            _settingsState.emit(settingsState.value.copy(lang = lang))
            repo.notifySettingsChanged()

        }
    }
}

