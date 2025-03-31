package com.example.cumulora.features.alarm

import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.data.models.alarm.Alarm


sealed class AlarmStateResponse {

    object Loading : AlarmStateResponse()

    data class Success(val data: List<Alarm>) : AlarmStateResponse()

    data class Failure(val error: String) : AlarmStateResponse()
}