package com.example.cumulora.utils

// LanguageUtils.kt
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.activity.ComponentActivity
import java.util.*

fun Context.applyLanguage(languageCode: String): Context {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val config = Configuration(resources.configuration)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    } else {
        config.locale = locale
        config.setLayoutDirection(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
    return this
}

fun Context.restartActivity() {
    (this as? ComponentActivity)?.recreate()
}