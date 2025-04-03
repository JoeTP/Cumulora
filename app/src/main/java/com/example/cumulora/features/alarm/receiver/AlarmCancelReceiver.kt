
package com.example.cumulora.features.alarm.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.cumulora.R
import com.example.cumulora.core.objects.MyMediaPlayer
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.alarm.manager.AlarmSchedulerImpl
import com.example.cumulora.utils.repoInstance
import com.google.android.libraries.places.api.model.LocalDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime

class AlarmCancelReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val alarmId = intent.getIntExtra("ALARM_ID", -1)
        if (alarmId == -1) return

        val isDeleteAction = intent.action == "DELETE" || intent.getBooleanExtra("isDismiss", false)
        val isSnooze = intent.action == "SNOOZE"

        Log.i("CANCEL RECEIVER", "onReceive: $isDeleteAction")
        CoroutineScope(Dispatchers.IO).launch {
            val repository = repoInstance(context)
            val alarm = repository.getAlarm(alarmId)

            alarm?.let {
                if (!isDeleteAction) {
                    AlarmSchedulerImpl(context).cancelAlarm(it)
                    repository.deleteAlarm(it)
                }

                if(isSnooze){
                    AlarmSchedulerImpl(context).cancelAlarm(it)
                    val newAlarm = it.also {
                        it.time = LocalTime.now().plusMinutes(5)
                    }
                    repository.deleteAlarm(it)
                    Log.d("CANCEL RECEIVER", "onReceive: ${newAlarm.time}")
                    repository.addAlarm(newAlarm)
                    AlarmSchedulerImpl(context).schedulerAlarm(it)
                }

                MyMediaPlayer.stop()
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(alarmId)

                Log.d("AlarmCancelReceiver", "Alarm ${it.id} cancelled")
            }
        }
    }
}