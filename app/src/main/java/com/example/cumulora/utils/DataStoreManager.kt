package com.example.cumulora.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private var sharedPref: SharedPreferences =
        context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var instance: SharedPrefManager? = null

        fun initialize(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SharedPrefManager(context.applicationContext)
                    }
                }
            }
        }

        fun getInstance(): SharedPrefManager {
            return instance ?: throw IllegalStateException("SharedPrefManager must be initialized first!")
        }
    }
    fun <T> saveData(key: String, value: T) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
            is Double -> editor.putFloat(key, value.toFloat())
        }
        editor.apply()
    }

    fun getString(key: String, defaultValue: String): String? = sharedPref.getString(key, defaultValue)

    fun getDouble(key: String, defaultValue: Double): Float = sharedPref.getFloat(key, defaultValue.toFloat())

    fun getBoolean(key: String, defaultValue: Boolean): Boolean = sharedPref.getBoolean(key, defaultValue)


    fun clearKey(key: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }
}