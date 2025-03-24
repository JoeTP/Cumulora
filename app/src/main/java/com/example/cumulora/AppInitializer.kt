package com.example.cumulora

import android.app.Application
import android.content.Context

class AppInitializer : Application() {

    companion object {
        private lateinit var instance: AppInitializer
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}