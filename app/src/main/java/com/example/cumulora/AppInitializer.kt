package com.example.cumulora

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.appcompat.app.AppCompatDelegate
import com.example.cumulora.features.alarm.receiver.AlarmReceiver
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
