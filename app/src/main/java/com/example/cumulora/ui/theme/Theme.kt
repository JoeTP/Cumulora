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

private val SunriseColorScheme = lightColorScheme(
    primary = BaseRise,
    surface = surfaceRiseColor,
    tertiary = TertiaryRise,
    primaryContainer = BaseRise,
    secondaryContainer = SecondaryRise,
    //FAB ICON
    onPrimaryContainer = Color.White,
    onBackground = TextRiseColor,
    onSurface = Color.White,
//    background = Color.Green,
)

private val DayColorScheme = lightColorScheme(
    primary = BaseDay,
    surface = surfaceDayColor,
    tertiary = TertiaryDay,
    primaryContainer = SecondaryDay,
    secondaryContainer = SecondaryDay,
    //FAB ICON
    onPrimaryContainer = Color.White,
    onBackground = TextDayColor,
    onSurface = Color.White,
//    background = Color.Green,
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
//    background = Color.Green,
)

private val NightColorScheme = darkColorScheme(
    primary = BaseNight,
    surface = SurfaceNightColor,
    tertiary = TertiaryNight,
    onTertiaryContainer = Color.White,
    tertiaryContainer = SecondaryNight,
    primaryContainer = SecondaryNight,
    secondaryContainer = SecondaryNight,
    background = BackgroundNightColor,
    //FAB ICON
    onPrimaryContainer = Color.White,
    onBackground = TextNightColor,
    onSurface = Color.White,
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