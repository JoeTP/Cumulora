package com.example.cumulora

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
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
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPreferenceHelper.initSharedPref(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}