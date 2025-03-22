package com.example.cumulora.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToDdMmm(dateTimeString: String): Pair<String, String> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM")
    val formattedDate = dateTime.format(dateFormatter)

    val dayName = dateTime.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    return Pair<String, String>(dayName, formattedDate)
}