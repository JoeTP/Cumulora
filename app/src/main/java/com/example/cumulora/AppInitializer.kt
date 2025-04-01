package com.example.cumulora

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.appcompat.app.AppCompatDelegate
import com.example.cumulora.features.alarm.receiver.AlarmReceiver
import com.example.cumulora.data.local.sharedpref.SharedPreferenceHelper

class AppInitializer : Application() {

    companion object {
        private lateinit var instance: AppInitializer
            private set

        fun getInstance(): AppInitializer {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPreferenceHelper.initSharedPref(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


}

@SuppressLint("ScheduleExactAlarm")
fun Context.setAlarm(seconds: Int) {
    val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(this, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    val triggerTime = SystemClock.elapsedRealtime() + (seconds * 1000)
    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
}