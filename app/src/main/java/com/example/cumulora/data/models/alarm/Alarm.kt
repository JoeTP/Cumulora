package com.example.cumulora.data.models.alarm

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cumulora.data.local.weather.SavedWeatherTypeConverter
import com.example.cumulora.utils.ALARMS_TABLE
import java.time.LocalTime

@Entity(tableName = ALARMS_TABLE)
@TypeConverters(AlarmTypeConverter::class)
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val label: String,
    val time: LocalTime,
    val duration: Int,
)