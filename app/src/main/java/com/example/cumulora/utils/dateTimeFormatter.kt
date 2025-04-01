package com.example.cumulora.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateToDdMmm(dateTimeString: String): Pair<String, String> {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(dateTimeString)

    val dateFormat = SimpleDateFormat("dd-MMM", Locale.getDefault())
    val formattedDate = dateFormat.format(date)

    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dayName = dayFormat.format(date)

    return Pair(dayName, formattedDate)
}

@SuppressLint("NewApi")
fun formatUnixTimeToHHMM(unixTimestamp: Long?): String {
    val instant = unixTimestamp?.let { Instant.ofEpochSecond(it) }
    val formatter = DateTimeFormatter.ofPattern("HH:mm a")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@Composable
fun formatTimeTo12Hour(time24Hour: String): String {
    val parts = time24Hour.split(":")
    if (parts.size < 2) return time24Hour

    val hour = parts[0].toInt()
    val minute = parts[1]

    return when {
        hour == 0 -> "12:$minute "
        hour < 12 -> "$hour:$minute "
        hour == 12 -> "12:$minute "
        else -> "${hour - 12}:$minute "
    }
}