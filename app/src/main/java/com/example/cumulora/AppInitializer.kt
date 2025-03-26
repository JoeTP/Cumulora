package com.example.cumulora

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import java.util.Locale

class AppInitializer : Application() {

    companion object {
        private lateinit var instance: AppInitializer
            private set

//        fun applyLanguage(context: Context): Context {
//            val locale = Locale(SharedPreferenceHelper.getInstance().getData("lang", "en"))
//            Locale.setDefault(locale)
//
//            val config = Configuration(context.resources.configuration)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                config.setLocale(locale)
//                config.setLayoutDirection(locale)
//                return context.createConfigurationContext(config)
//            } else {
//                config.locale = locale
//                config.setLayoutDirection(locale)
//                context.resources.updateConfiguration(config, context.resources.displayMetrics)
//            }
//            return context
//        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPreferenceHelper.initSharedPref(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}