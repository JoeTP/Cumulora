package com.example.cumulora.ui.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.weatherIcons
import kotlin.math.abs

@Composable
fun OvalCard(forecast: Forecast, lang: String) {
    val hours24 = forecast.dtTxt.split(" ", ":")
    //TODO: ENUM for AM PM
    val am_pm = if (hours24[1].toInt() < 12) stringResource(R.string.am) else stringResource(R.string.pm)
    val hours12 = if (hours24[1].toInt() > 12 || hours24[1].toInt() == 0)
        abs(hours24[1].toInt() - 12)
    else hours24[1].toInt()

    Surface(shape = CircleShape) {
        Column(
            modifier = Modifier
                .height(150.dp)
                .width(70.dp)
                .padding(vertical = 15.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("${hours12.toString().formatNumberBasedOnLanguage(lang)} $am_pm")
            Image(
                painter = painterResource(id = weatherIcons.getValue(forecast.weather.first().icon)),
                contentDescription = ""
            )
            Text(forecast.main.temp.toInt().toString().formatNumberBasedOnLanguage(lang)+ " " +
                    stringResource(R
                .string.k))
        }
    }
}