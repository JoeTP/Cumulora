package com.example.cumulora.core.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.settings.SettingsViewModel


class SettingsViewModelFactory(private val repo: WeatherRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repo) as T
    }
}