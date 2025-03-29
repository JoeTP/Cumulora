package com.example.cumulora.features.savedweather.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.formatUnixTimeToHHMM
import com.example.cumulora.utils.weatherIcons

@SuppressLint("NewApi")
@Composable
fun SavedWeatherCard(savedWeather: SavedWeather) {

    val weather = savedWeather.weatherResponse
    val forecast = savedWeather.forecastResponse
    val cityName = savedWeather.cityName

    val tempMax = weather?.main?.tempMax?.toInt().toString().formatNumberBasedOnLanguage(CURRENT_LANG)
    val tempMin = weather?.main?.tempMin?.toInt().toString().formatNumberBasedOnLanguage(CURRENT_LANG)

    Surface(color = Color.Gray, shape = RoundedCornerShape(20.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(cityName)
                Text("$tempMax / $tempMin (F)")
                //TODO: Make it small text
                Text(stringResource(R.string.last_updated, formatUnixTimeToHHMM(weather?.dt)))
            }
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(
                    weatherIcons.getValue(
                        weather?.weatherList?.first()?.icon ?: "01d"
                    )
                ),
                contentDescription = ""
            )
        }
    }

}