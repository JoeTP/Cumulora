package com.example.cumulora.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.cumulora.core.enums.DayPeriod

private val NightColorScheme = darkColorScheme(

    onPrimaryContainer = Color.White,
)

private val DayColorScheme = lightColorScheme(
    primary = BaseDay,
    secondary = SecondaryDay,
    tertiary = TertiaryDay,
    primaryContainer = SecondaryDay,
    secondaryContainer = SecondaryDay,
    onBackground = TextDayColor,
//    surface = Color(0xFFDEDEDE),
    onPrimaryContainer = Color.White,
)

private val SunriseColorScheme = lightColorScheme(
    primary = Color(0xFFF9A825),
    secondary = Color(0xFFFFD54F),
    tertiary = Color(0xFFFFF176),
    primaryContainer = Color(0xFFFFB300),
    tertiaryContainer = Color(0xFFFFE082),
    secondaryContainer = Color(0xFFFFC107),
    onPrimaryContainer = Color.White,
    inversePrimary = Color(0xFFFFD740)
)

private val SunsetColorScheme = lightColorScheme(
    primary = BaseSet,
    surface = surfaceSetColor,
    tertiary = TertiarySet,
    primaryContainer = BaseSet,
    secondaryContainer = SecondarySet,
    //FAB ICON
    onPrimaryContainer = Color.White,
    onBackground = TextSetColor,
    onSurface = Color.White,
    background = Color.Green,
)

@Composable
fun CumuloraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    dayPeriod: DayPeriod? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        dayPeriod != null -> when (dayPeriod) {
            DayPeriod.SUNRISE -> SunriseColorScheme
            DayPeriod.SUNSET -> SunsetColorScheme
            DayPeriod.DAYTIME -> DayColorScheme
            DayPeriod.NIGHTTIME -> NightColorScheme
            else -> if (darkTheme) NightColorScheme else DayColorScheme
        }

        darkTheme -> NightColorScheme
        else -> DayColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}