package com.example.cumulora.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

//@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToDdMmm(dateTimeString: String): Pair<String, String> {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = inputFormat.parse(dateTimeString)

    val dateFormat = SimpleDateFormat("dd-MMM", Locale.getDefault())
    val formattedDate = dateFormat.format(date)

    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val dayName = dayFormat.format(date)

    return Pair(dayName, formattedDate)
}