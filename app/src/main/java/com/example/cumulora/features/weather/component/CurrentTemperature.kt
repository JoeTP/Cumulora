package com.example.cumulora.features.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.R
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.weatherIcons

@Composable
fun CurrentTemperature(
    cityName: String,
    temperature: Double,
    feelsLike: Double,
    description: String,
    time: String,
    icon: String,
    onMapNavigate: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)
        .background(Color.Transparent)) {
        Row(
            modifier = Modifier.clickable { onMapNavigate() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Map")
            Text(cityName)
        }
        Column(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = weatherIcons.getValue(icon)),
                contentDescription = ""
            )
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = temperature.toString().formatNumberBasedOnLanguage(CURRENT_LANG),
                    textAlign = TextAlign.Center,
                    fontSize = 54.sp
                )
                Text(stringResource(R.string.c))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.temp_feel_like), contentDescription = "Map")
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.feels_like, feelsLike.toString().formatNumberBasedOnLanguage(CURRENT_LANG)))
            }
            Text(description)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = ""
            )
            Text(stringResource(R.string.last_updated, time))
        }

    }
}