
package com.example.cumulora.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.example.cumulora.R
import com.example.cumulora.core.objects.MyMediaPlayer
import com.example.cumulora.manager.AlarmSchedulerImpl
import com.example.cumulora.utils.repoInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmCancelReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val alarmId = intent.getIntExtra("ALARM_ID", -1)
        if (alarmId == -1) return

        val isDeleteAction = intent.action == "DELETE" || intent.getBooleanExtra("isDismiss", false)

        Log.i("CANCEL RECEIVER", "onReceive: $isDeleteAction")
        CoroutineScope(Dispatchers.IO).launch {
            val repository = repoInstance(context)
            val alarm = repository.getAlarm(alarmId)

            alarm?.let {
                if (!isDeleteAction) {
                    AlarmSchedulerImpl(context).cancelAlarm(it)
                    repository.deleteAlarm(it)
                }

                MyMediaPlayer.stop()
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(alarmId)

                Log.d("AlarmCancelReceiver", "Alarm ${it.id} cancelled")
            }
        }
    }
}