package com.example.cumulora.features.alarm.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.data.models.alarm.Alarm
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@SuppressLint("NewApi")
@Composable
fun AlarmCard(alarm: Alarm) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray, shape = RoundedCornerShape(12.dp)).padding(12.dp), horizontalArrangement
        = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val time = formatTime24to12(alarm.time.hour, alarm.time.minute)
        Column (verticalArrangement = Arrangement.Center){
            Text(time, fontSize = 22.sp)
            if(alarm.label.isNotEmpty()){
                Text(alarm.label, fontSize = 12.sp)
            }
        }
        Text(alarm.cityName)
    }
}

fun formatTime24to12(hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }

    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(calendar.time)
}