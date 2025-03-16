package com.example.cumulora.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStoreManager(private val context: Context) {

    companion object {
        val IS_FIRST_TIME = booleanPreferencesKey(IS_FIRST_TIME_SK)
        val USE_CURRENT_LOC = booleanPreferencesKey(USE_CURRENT_LOC_SK)
        val LANG = stringPreferencesKey(LANG_SK)
        suspend fun <T> saveData(context: Context, key: Preferences.Key<T>, value: T) {
            context.dataStore.edit { preferences ->
                when (value) {
                    is Int -> preferences[intPreferencesKey(key.name)] = value
                    is String -> preferences[stringPreferencesKey(key.name)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key.name)] = value
                    is Double -> preferences[floatPreferencesKey(key.name)] = value.toFloat()
                }
            }
        }

        suspend fun <T> getData(context: Context, key: Preferences.Key<T>, defaultValue: T): T {
            val preferences = context.dataStore.data.first()
            return preferences[key] ?: defaultValue
        }
    }
}