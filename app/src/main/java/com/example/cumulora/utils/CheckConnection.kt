package com.example.cumulora.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.cumulora.AppInitializer


fun isInternetAvailable(): Boolean {
    val connectivityManager = AppInitializer.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as
            ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}