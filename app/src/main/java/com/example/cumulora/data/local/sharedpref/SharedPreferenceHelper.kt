package com.example.cumulora.data.local.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.example.cumulora.utils.SHARED_PREF_NAME

class SharedPreferenceHelper private constructor(context: Context) : SharedPref {


    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)



    companion object{

        @Volatile
        private var INSTANCE: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferenceHelper(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }

    }


    override fun <T> saveData(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Double -> putFloat(key, value.toFloat())
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    override fun <T> getData(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Double -> sharedPreferences.getFloat(key, defaultValue.toFloat()).toDouble() as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }


}