package com.example.cumulora.features.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.example.cumulora.component.OvalCard
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage


@Composable
fun TodayTab(weather: WeatherEntity, forecast: ForecastResponse) {

    val forecastList = forecast.forecastList

    Column(modifier = Modifier.background(Color.Transparent)) {
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

        LazyVerticalGrid(
//            contentPadding = PaddingValues(bottom = 82.dp),
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .height(400.dp)
                .padding(horizontal = 16.dp),
//            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Humidity(weather.humidity.toString().formatNumberBasedOnLanguage(CURRENT_LANG))
            }
            item {
                Wind(weather.windSpeed.toString().formatNumberBasedOnLanguage(CURRENT_LANG), weather.windDegree.toFloat())
            }
            item {
                Pressure(weather.pressure.toString().formatNumberBasedOnLanguage(CURRENT_LANG))
            }
            item {
                Clouds(weather.clouds.toString().formatNumberBasedOnLanguage(CURRENT_LANG))
            }
        }
    }
}

@Composable
private fun Wind(windSpeed: String, windDegree: Float) =
    WeatherCard(stringResource(R.string.wind_speed), R.drawable.wind) {
        Box(contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.compas), contentDescription = "")
            Image(
                painter = painterResource(id = R.drawable.compas_arrow), contentDescription = "",
                modifier = Modifier.rotate(windDegree)
            )
            Text(windSpeed)
//            TODO("DONT FORGET THE UNIT")
        }
    }


@Composable
private fun Humidity(humidity: String) = WeatherCard(stringResource(R.string.humidity), R.drawable.humidity) {
    Text(humidity)
}


@Composable
private fun Pressure(pressure: String) = WeatherCard(stringResource(R.string.pressure), R.drawable.pressure) {
    Text(pressure)
}

@Composable
private fun Clouds(clouds: String) = WeatherCard(stringResource(R.string.clouds), R.drawable.cloud_icon) {
    Text(clouds)
}
