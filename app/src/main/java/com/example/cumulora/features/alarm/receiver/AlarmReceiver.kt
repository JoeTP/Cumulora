package com.example.cumulora.features.alarm.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.cumulora.R
import com.example.cumulora.core.objects.MyMediaPlayer
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.repoInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    val TAG = "AlarmReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val alarmId = intent.getIntExtra("ALARM_ID", -1)
        val label = intent.getStringExtra("MSG") ?: return
        if (alarmId == -1) return

        val repository = repoInstance(context)

        CoroutineScope(Dispatchers.IO).launch {
            val alarm = repository.getAlarm(alarmId) ?: return@launch
            val lat = repository.getCachedData(LAST_LAT, "0.0").toDouble()
            val lon = repository.getCachedData(LAST_LAT, "0.0").toDouble()
            val lang = repository.getCachedData(LANG, CURRENT_LANG)
            val weatherFlow = repository.getWeather(lat, lon, null, lang)
            var weatherDescription = "Weather data not available"

            Log.d(TAG, "THE RETURNED ALARM: ${alarm.id} ${alarm.time} ${alarm.duration}")

            weatherFlow.collect { weatherResponse ->
                weatherResponse?.weatherList?.get(0)?.description?.let {
                    weatherDescription = it
                }
            Log.i(TAG, "onReceive: $weatherDescription $lang $lat $lon")

//                return@collect
            }

            createNotification(context, alarmId, label, weatherDescription)
//            context.startService(Intent(context, AlarmService::class.java).apply {
//                action = "START"
//            })
            playAudio(context)
            Log.d(
                "AlarmReceiver",
                "Alarm ${alarm.id} triggered, will auto-cancel in ${alarm.duration} seconds"
            )
        }
    }

    private fun playAudio(context: Context) {
        MyMediaPlayer.play(context)
    }

    private fun createNotification(
        context: Context,
        alarmId: Int,
        label: String,
        weatherDescription: String,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarm Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for alarm notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val deleteIntent = Intent(context, AlarmCancelReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            action = "DELETE"
        }
        val deletePendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val cancelIntent = Intent(context, AlarmCancelReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
        }
        val cancelPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozeIntent = Intent(context, AlarmCancelReceiver::class.java).apply {
            putExtra("ALARM_ID", alarmId)
            action = "SNOOZE"
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "ALARM_CHANNEL")
            .setContentTitle(context.getString(R.string.alarm, label))
            .setContentText(context.getString(R.string.current_weather, weatherDescription))
            .setSmallIcon(R.drawable.shower_rain)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .addAction(
                R.drawable.broken_clouds,
                context.getString(R.string.cancel),
                cancelPendingIntent
            )
            .addAction(
                R.drawable.broken_clouds,
                context.getString(R.string.snooze_5_mins),
                snoozePendingIntent
            )
            .setDeleteIntent(deletePendingIntent)
            .build()

        notificationManager.notify(alarmId, notification)
    }
}