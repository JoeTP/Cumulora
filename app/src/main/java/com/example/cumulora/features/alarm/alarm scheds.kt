package com.example.cumulora.features.alarm

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.utils.ALARM_ID_KEY
import com.example.cumulora.utils.ALARM_LABEL_KEY
import com.example.cumulora.utils.DURATION_KEY
import com.example.cumulora.manager.AlarmWorker
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit


@RequiresApi(Build.VERSION_CODES.O)
 fun scheduleAlarmWork(alarm: Alarm, context: Context, onInsert: (Alarm)-> Unit) {
    if (!alarm.isActive) return

    val now = LocalTime.now()
    val alarmTime = alarm.time
    var initialDelay = Duration.between(now, alarmTime).toMinutes()

    // If alarm time is in the past, schedule for next day
    if (initialDelay < 0) {
        initialDelay += 24 * 60
    }

    val inputData = workDataOf(
        ALARM_ID_KEY to alarm.id,
        ALARM_LABEL_KEY to alarm.label,
        DURATION_KEY to alarm.duration
    )

    val alarmWorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
        .setInputData(inputData)
        .setInitialDelay(/*initialDelay*/5, TimeUnit.SECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "alarm_${alarm.id}",
            ExistingWorkPolicy.REPLACE,
            alarmWorkRequest
        )

    alarm.id = alarmWorkRequest.id.version()
    onInsert(alarm)
}

 fun cancelAlarmWork(alarmId: Int, context: Context) {
    WorkManager.getInstance(context)
        .cancelUniqueWork("alarm_$alarmId")
}