package com.example.cumulora.utils

import android.content.Context
import com.example.cumulora.R

fun getTemperatureUnit(setting: String): String {
    return when {
        setting.contains("°F", ignoreCase = true) || setting.contains("°ف", ignoreCase = true) -> "imperial"
        setting.contains("°C", ignoreCase = true) || setting.contains("°س", ignoreCase = true) -> "metric"
        setting.contains("K", ignoreCase = true) || setting.contains("ك", ignoreCase = true) -> "standard"
        setting.contains("m/s", ignoreCase = true) || setting.contains("م/ث", ignoreCase = true) -> "metric"
        setting.contains("mph", ignoreCase = true) || setting.contains("ميل/س", ignoreCase = true) -> "imperial"
        else -> "Unknown Unit"
    }
}

fun Context.getTempUnitSymbol(unit: String): String {
    return when (unit) {
        "standard" -> getString(R.string.k)
        "metric" -> getString(R.string.c)
        "imperial" -> getString(R.string.f)
        else -> getString(R.string.c)
    }
}

fun Context.getWindSpeedUnitSymbol(unit: String): String {
    return when (unit) {
        "standard" -> getString(R.string.ms)
        "metric" -> getString(R.string.ms)
        "imperial" -> getString(R.string.mph)
        else -> getString(R.string.ms)
    }
}