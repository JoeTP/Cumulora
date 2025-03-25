package com.example.cumulora

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper

class AppInitializer : Application() {

    companion object {
        private lateinit var instance: AppInitializer
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPreferenceHelper.initSharedPref(this)
    }
}