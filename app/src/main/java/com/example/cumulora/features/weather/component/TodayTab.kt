package com.example.cumulora.features.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.ui.component.OvalCard
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.DayNightIndicator
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.formatTimeTo12Hour
import com.example.cumulora.utils.formatUnixTimeToHHMM


@Composable
fun TodayTab(weather: WeatherEntity, forecast: ForecastResponse) {

    val forecastList = forecast.forecastList

    Column(
        modifier = Modifier
            .background(Color.Transparent)
//            .padding(horizontal = 16.dp)
            .padding(bottom = 28.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = 8) { index ->
                OvalCard(forecastList[index], CURRENT_LANG)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        WeatherCard(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Column {
                DetailsRow(
                    stringResource(
                        R.string.max_min
                    ), weather.maxTemp.toInt().toString().formatNumberBasedOnLanguage
                        (CURRENT_LANG)
                ) {
                    Icon(imageVector = Icons.Default.Thermostat, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                DetailsRow(
                    stringResource(R.string.pressure), weather.pressure.toString()
                        .formatNumberBasedOnLanguage(CURRENT_LANG)
                ) {
                    Icon(imageVector = Icons.Default.Compress, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                DetailsRow(
                    stringResource(R.string.humidity), weather.humidity.toString()
                        .formatNumberBasedOnLanguage(CURRENT_LANG)
                ) {
                    Icon(imageVector = Icons.Default.WaterDrop, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                DetailsRow(
                    stringResource(R.string.clouds), weather.clouds.toString()
                        .formatNumberBasedOnLanguage(CURRENT_LANG)
                ) {
                    Icon(imageVector = Icons.Default.Cloud, contentDescription = "")
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                //TODO: UNIT
                Row(
                    Modifier.fillMaxSize(), horizontalArrangement = Arrangement
                        .SpaceBetween
                ) {
                    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                        Row {
                            Icon(imageVector = Icons.Default.Air, contentDescription = "")
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(stringResource(R.string.wind_speed))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(weather.windSpeed.toString().formatNumberBasedOnLanguage(CURRENT_LANG))

                    }
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier.size(60.dp),
                            painter = painterResource(id = R.drawable.compas),
                            contentDescription = ""
                        )
                        Image(
                            painter = painterResource(id = R.drawable.compas_arrow), contentDescription = "",
                            modifier = Modifier
                                .rotate(weather.windDegree.toFloat())
                                .size(60.dp)
                        )
                        Text(weather.windDegree.toString().formatNumberBasedOnLanguage(CURRENT_LANG) + "°")

                    }
                }
            }
        }

//        WeatherCard(stringResource(R.string.wind_speed), icon = {
//            Icon(
//                imageVector = Icons.Default.Air,
//                contentDescription = ""
//            )
//        }) {
//            Row {
//                Text("TEST")
//                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                    Image(
//                        modifier = Modifier.size(80.dp), painter = painterResource(id = R.drawable.compas),
//                        contentDescription = ""
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.compas_arrow), contentDescription = "",
//                        modifier = Modifier.rotate(weather.windDegree.toFloat()).size(80.dp)
//                    )
//                    Text(weather.windSpeed.toString().formatNumberBasedOnLanguage(CURRENT_LANG))
//                }
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp))

        val sunrise24 = formatUnixTimeToHHMM(weather.sunRise).formatNumberBasedOnLanguage(CURRENT_LANG)
        val sinSet24 = formatUnixTimeToHHMM(weather.sunSet).formatNumberBasedOnLanguage(CURRENT_LANG)
        val sunrise = formatTimeTo12Hour(sunrise24).formatNumberBasedOnLanguage(CURRENT_LANG)
        val sinSet = formatTimeTo12Hour(sinSet24).formatNumberBasedOnLanguage(CURRENT_LANG)
        WeatherCard(
            stringResource(R.string.sunrise_sunset), subtitle = "$sunrise / $sinSet", icon = {
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = ""
                )
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            DayNightIndicator(
                modifier = Modifier.padding(vertical = 18.dp), sunriseUnix = weather.sunRise,
                sunsetUnix =
                weather.sunSet
            )
        }
    }
}

@Composable
fun DetailsRow(title: String, trail: String, icon: @Composable () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
            Spacer(modifier = Modifier.width(12.dp))
            Text(title);
        }
        Text(trail)
    }
}