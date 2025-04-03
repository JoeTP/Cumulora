package com.example.cumulora.data.models.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cumulora.utils.ALARMS_TABLE
import java.time.LocalTime

@Entity(tableName = ALARMS_TABLE)
@TypeConverters(AlarmTypeConverter::class)
data class Alarm(
    @PrimaryKey
    var id: Int,
    val label: String,
    val cityName: String,
    var time: LocalTime,
    val duration: Int,
)