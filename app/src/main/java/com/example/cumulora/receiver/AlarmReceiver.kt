package com.example.cumulora.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.cumulora.utils.repoInstance

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
//            repoInstance(context).
        }

        val label = intent?.getStringExtra("MSG") ?: return
        Log.d("RECEIVER", "onReceive: $label")
    }

}