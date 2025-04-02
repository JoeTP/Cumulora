package com.example.cumulora.features.map.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cumulora.utils.isLocationEnabled

class LocationReceiver(private val onStatusChanged: (Boolean) -> Unit) : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val isEnabled = isLocationEnabled(it)
            onStatusChanged(isEnabled)
        }
    }
}
