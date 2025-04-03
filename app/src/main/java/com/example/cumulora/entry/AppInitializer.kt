package com.example.cumulora.entry

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.cumulora.BuildConfig
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper
import com.google.android.libraries.places.api.Places

class AppInitializer : Application() {

    companion object {
        private lateinit var instance: AppInitializer
            private set

        fun getInstance(): AppInitializer {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        SharedPreferenceHelper.initSharedPref(this)
        Places.initializeWithNewPlacesApiEnabled(this, BuildConfig.googleApiKey)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


}
