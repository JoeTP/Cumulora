package com.example.cumulora.features.alarm.manager

import com.example.cumulora.data.models.alarm.Alarm

interface AlarmScheduler {

    fun schedulerAlarm(alarm: Alarm)

    fun cancelAlarm(alarm: Alarm)

}