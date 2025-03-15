package com.example.cumulora.features.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    var isSplashVisible by mutableStateOf(true)

    fun hideSplash() {
        viewModelScope.launch {
            delay(1900)
            isSplashVisible = false
        }
    }
}