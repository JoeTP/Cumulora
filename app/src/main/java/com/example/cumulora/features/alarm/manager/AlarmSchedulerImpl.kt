package com.example.cumulora.features.alarm.manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.alarm.receiver.AlarmCancelReceiver
import com.example.cumulora.features.alarm.receiver.AlarmReceiver
import java.time.LocalDate
import java.time.ZoneId

class AlarmSchedulerImpl(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedulerAlarm(alarm: Alarm) {

        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("MSG", alarm.label)
            putExtra("ALARM_ID", alarm.id)
        }

        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id!!,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val cancelIntent = Intent(context, AlarmCancelReceiver::class.java).apply {
            putExtra("ALARM_ID", alarm.id)
        }

        val cancelPendingIntent = PendingIntent.getBroadcast(
            context,
            -alarm.id!!,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmTime = alarm.time.atDate(LocalDate.now())
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val cancelTime = alarmTime + (alarm.duration * 1000L)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                alarmPendingIntent
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                cancelTime,
                cancelPendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                alarmPendingIntent
            )
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                cancelTime,
                cancelPendingIntent
            )
        }
    }

    override fun cancelAlarm(alarm: Alarm) {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val cancelIntent = Intent(context, AlarmCancelReceiver::class.java)

        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarm.id!!,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val cancelPendingIntent = PendingIntent.getBroadcast(
            context,
            -alarm.id!!,
            cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(alarmPendingIntent)
        alarmManager.cancel(cancelPendingIntent)
    }
}
