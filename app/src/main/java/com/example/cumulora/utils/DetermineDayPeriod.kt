package com.example.cumulora.utils

import com.example.cumulora.core.enums.DayPeriod

fun determineDayPeriod(
    currentTime: Long,
    sunriseTime: Long,
    sunsetTime: Long,
    marginMinutes: Int = 30
): DayPeriod {
    val marginSeconds = marginMinutes * 60

    return when {
        currentTime in (sunriseTime - marginSeconds)..(sunriseTime + marginSeconds) ->
            DayPeriod.SUNRISE

        currentTime in (sunsetTime - marginSeconds)..(sunsetTime + marginSeconds) ->
            DayPeriod.SUNSET

        currentTime in sunriseTime..sunsetTime ->
            DayPeriod.DAYTIME

        else ->
            DayPeriod.NIGHTTIME
    }
}