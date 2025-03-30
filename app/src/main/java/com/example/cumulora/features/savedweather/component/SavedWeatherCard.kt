package com.example.cumulora.features.savedweather.component

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.R
import com.example.cumulora.data.local.weather.SavedWeather
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.getTempUnitSymbol
import com.example.cumulora.utils.weatherIcons

@SuppressLint("NewApi")
@Composable
fun SavedWeatherCard(savedWeather: SavedWeather, tempUnit: String) {
    Log.i("TAG", "SavedWeatherCard: $tempUnit")

    val ctx = LocalContext.current
    val weather = savedWeather.weather
//    val forecast = savedWeather.forecastResponse
    val cityName = savedWeather.cityName

    val tempMax = weather?.tempMax
    val tempMin = weather?.tempMin

    val (displayMax, displayMin) = when (tempUnit.lowercase()) {
        "standard" -> {
            //K = °C + 273.15
            Pair(
                tempMax?.let { (it + 273.15).toInt() },
                tempMin?.let { (it + 273.15).toInt() }
            )
        }
        "imperial" -> {
            //F = (°C × 9/5) + 32
            Pair(
                tempMax?.let { (it * 9 / 5 + 32).toInt() },
                tempMin?.let { (it * 9 / 5 + 32).toInt() }
            )
        }
        else -> {
            Pair(
                tempMax?.toInt(),
                tempMin?.toInt()
            )
        }
    }


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
                Text("$displayMax / $displayMin ${ctx.getTempUnitSymbol(tempUnit)}")
                //TODO: FIX THE TIME FORMAT
                Text(stringResource(R.string.last_updated, weather?.currentTime.toString()
                .formatNumberBasedOnLanguage(CURRENT_LANG)), fontSize = 12.sp)
            }
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(
                    weatherIcons.getValue(
                        weather?.icon ?: "01d"
                    )
                ),
                contentDescription = ""
            )
        }
    }
}