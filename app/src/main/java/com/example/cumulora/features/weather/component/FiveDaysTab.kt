package com.example.cumulora.features.weather.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatDateToDdMmm
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.weatherIcons


@Composable
fun FiveDaysTab(forecastFiveDays: List<Forecast>, tempUnit: String, windUnit: String) {

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
                color = MaterialTheme.colorScheme.secondaryContainer.copy(0.2f),
            ),
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            for (i in 0..4) {
                ForecastItem(forecastFiveDays[i], tempUnit, windUnit)
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: Forecast, tempUnit: String, windUnit: String) {
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
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),

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
                    .padding(16.dp)
            ) {
                ForecastDetails(forecast, tempUnit, windUnit)
            }
        }
    }
}

@Composable
fun ForecastDetails(forecast: Forecast, tempUnit: String, windUnit: String) {

    WeatherCard{
        Column {
            DetailsRow(
                stringResource(
                    R.string.max_min
                ),
                forecast.main.tempMax.toInt().toString().formatNumberBasedOnLanguage
                    (CURRENT_LANG) + " / " + forecast.main.tempMin.toInt().toString().formatNumberBasedOnLanguage(CURRENT_LANG), tempUnit
            ) {
                Icon(imageVector = Icons.Default.Thermostat, contentDescription = "")
            }
            HorizontalDivider(Modifier.padding(vertical = 12.dp))
            DetailsRow(
                stringResource(R.string.pressure), forecast.main.pressure.toString()
                    .formatNumberBasedOnLanguage(CURRENT_LANG), stringResource(R.string.hpa)
            ) {
                Icon(imageVector = Icons.Default.Compress, contentDescription = "")
            }
            HorizontalDivider(Modifier.padding(vertical = 12.dp))
            DetailsRow(
                stringResource(R.string.humidity), forecast.main.humidity.toString()
                    .formatNumberBasedOnLanguage(CURRENT_LANG), "%"
            ) {
                Icon(imageVector = Icons.Default.WaterDrop, contentDescription = "")
            }
            HorizontalDivider(Modifier.padding(vertical = 12.dp))
            DetailsRow(
                stringResource(R.string.clouds), forecast.clouds.all.toString()
                    .formatNumberBasedOnLanguage(CURRENT_LANG), "%"
            ) {
                Icon(imageVector = Icons.Default.Cloud, contentDescription = "")
            }
        }
    }

}