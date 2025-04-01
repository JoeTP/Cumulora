package com.example.cumulora.features.weather.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.ui.theme.Purple
import com.example.cumulora.ui.theme.Purple40
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatDateToDdMmm
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.weatherIcons


@Composable
fun FiveDaysTab(forecastFiveDays: List<Forecast>, tempUnit: String) {

    Surface(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 28.dp
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(20.dp),
                color = Purple40.copy(alpha = 0.3f),
            ),
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            for (i in 0..4) {
                ForecastItem(forecastFiveDays[i], tempUnit)
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: Forecast, tempUnit:String) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotateState by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    Column {
        ListItem(modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 500,
                    easing = LinearEasing
                )
            )
            .clickable(onClick = { isExpanded = !isExpanded }),
            colors = ListItemDefaults.colors
                (
                containerColor = Purple
                    .copy(alpha = 0.2f)
            ),
            headlineContent = {
                Text(formatDateToDdMmm((forecast.dtTxt)).first)
            },
            supportingContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) { Text(formatDateToDdMmm(forecast.dtTxt).second) }
            },
            trailingContent = {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "${
                                forecast.main.tempMax.toInt().toString().formatNumberBasedOnLanguage
                                    (CURRENT_LANG)
                            } / " +
                                    forecast.main.tempMin.toInt().toString().formatNumberBasedOnLanguage
                                        (CURRENT_LANG) + " $tempUnit"
                        )
                        Text(forecast.weather.first().description)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = weatherIcons.getValue(forecast.weather.first().icon)),
                        contentDescription
                        = ""
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.rotate(rotateState),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
            })

        if (isExpanded) {
            Box(
                modifier = Modifier
                    .padding(start = 32.dp)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(forecast.weather.first().description)
                }
            }
        }
    }
}