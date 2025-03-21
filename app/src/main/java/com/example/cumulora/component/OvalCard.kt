package com.example.cumulora.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.utils.weatherIcons
import kotlin.math.abs

@Composable
fun OvalCard(forecast: Forecast) {
    val hours24 = forecast.dtTxt.split(" ", ":")
    val am_pm = if (hours24[1].toInt() < 12) "AM" else "PM"
    val hours12 = if (hours24[1].toInt() > 12 || hours24[1].toInt() == 0)
        abs(hours24[1].toInt() - 12)
    else hours24[1].toInt()

    Surface(shape = CircleShape) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .width(60.dp)
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$hours12 $am_pm")
            Image(
                painter = painterResource(id = weatherIcons.getValue(forecast.weather.first().icon)),
                contentDescription = ""
            )
            Text(forecast.main.temp.toString())
        }
    }
}