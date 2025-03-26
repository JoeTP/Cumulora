package com.example.cumulora.data.local.sharedpref

interface SharedPref {
    fun <T> saveData(key: String, value: T)
    fun <T> getData(key: String, defaultValue: T): T
}