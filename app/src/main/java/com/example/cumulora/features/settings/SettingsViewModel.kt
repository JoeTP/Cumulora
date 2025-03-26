package com.example.cumulora.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LOCATION_TYPE
import com.example.cumulora.utils.UNITS
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class SettingsViewModel(private val repo: WeatherRepository) : ViewModel() {
    private val _settingsState = MutableSharedFlow<SettingsState>(1)
    val settingsState = _settingsState.asSharedFlow()

    //notifier
//    private val _settingsChanged = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
//    val settingsChanged: SharedFlow<Unit> = _settingsChanged.asSharedFlow()


    init {
        loadInitialSettings()
    }

    private fun loadInitialSettings() {
        viewModelScope.launch {
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
            _settingsState.emit(settingsState.last().copy(unit = unit))
            repo.notifySettingsChanged()
        }
    }

    fun changeLocationType(locationType: String) {
        viewModelScope.launch {
            repo.cacheData("locationType", locationType)
            _settingsState.emit(settingsState.last().copy(locationType = locationType))
            repo.notifySettingsChanged()

        }
    }

    fun changeLang(lang: String) {
        viewModelScope.launch {
            repo.cacheData("lang", lang)
            _settingsState.emit(settingsState.last().copy(lang = lang))
            repo.notifySettingsChanged()

        }
    }
}

