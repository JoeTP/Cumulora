package com.example.cumulora.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.Locale


fun String.convertToArabicNumbers(): String {
    val englishNumbers = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val arabicNumbers = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')

    var result = this
    for (i in englishNumbers.indices) {
        result = result.replace(englishNumbers[i], arabicNumbers[i])
    }
    return result
}

fun String.formatNumberBasedOnLanguage(language: String): String {
    return if (language == "ar") this.convertToArabicNumbers() else this
}

fun restartActivity(context: Context) {
    val intent = (context as? Activity)?.intent
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    (context as? Activity)?.finish()
}