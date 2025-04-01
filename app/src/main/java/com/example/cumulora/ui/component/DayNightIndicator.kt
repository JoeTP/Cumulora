package com.example.cumulora.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.cumulora.ui.theme.DarkPurple
import com.example.cumulora.ui.theme.LightLinColorOne


@Composable
fun DayNightIndicator(
    sunriseUnix: Long = 1743043591,
    sunsetUnix: Long = 1743091442,
    currentTimeUnix: Long = System.currentTimeMillis() / 1000,
    modifier: Modifier = Modifier
) {
    val position = calculateSunPosition(
        sunriseUnix = sunriseUnix,
        sunsetUnix = sunsetUnix,
        currentTimeUnix = currentTimeUnix
    )


    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        val width = size.width
        val height = size.height
        val centerY = height / 2


        val colorStops = arrayOf(
            0.0f to DarkPurple,
            0.1f to DarkPurple.copy(0.3f),
            0.5f to LightLinColorOne,
            0.9f to DarkPurple.copy(0.3f),
            1.0f to DarkPurple
        )

        drawRect(
            brush = Brush.horizontalGradient(
                colorStops = colorStops,
                startX = 0f,
                endX = size.width
            ),
        )


        val circleX = width * position.coerceIn(0f..1f)
        drawCircle(
            color = LightLinColorOne,
            radius = height * 1.2f,
            center = Offset(circleX, centerY)
        )
    }
}

private fun calculateSunPosition(
    sunriseUnix: Long,
    sunsetUnix: Long,
    currentTimeUnix: Long
): Float {
    return when {
        currentTimeUnix <= sunriseUnix -> 0f
        currentTimeUnix >= sunsetUnix -> 1f
        else -> {
            val totalDayDuration = (sunsetUnix - sunriseUnix).toFloat()
            val elapsedSinceSunrise = (currentTimeUnix - sunriseUnix).toFloat()
            elapsedSinceSunrise / totalDayDuration
        }
    }
}