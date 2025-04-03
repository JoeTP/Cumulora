package com.example.cumulora.features.weather.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.R
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.formatUnixTimeToHHMM
import com.example.cumulora.utils.weatherIcons
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentTemperature(
    cityName: String,
    temperature: Double,
    description: String,
    time: String,
    date: String,
    feelsLike: String,
    icon: String,
    tempUnit: String,
    onMapNavigate: () -> Unit
) {
    val myTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm a")
    val formattedTime = myTime.format(formatter)
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Box(  contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .size(260.dp)
                    .alpha(0.4f),
                painter = painterResource(id = weatherIcons.getValue(icon)),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onMapNavigate() }
                        .padding(12.dp),
                    text = if (cityName.isEmpty()) stringResource(R.string.unknown_location) else cityName,
                    maxLines = 2,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = temperature.toInt().toString().formatNumberBasedOnLanguage(CURRENT_LANG),
                        textAlign = TextAlign.Center,
                        fontSize = 124.sp,
                        )
                    Text(modifier = Modifier.padding(top = 38.dp), text = tempUnit, fontSize = 24.sp)

                }
                Spacer(modifier = Modifier.height(16.dp))
                    Text(description)
                Row {
                    Icon(imageVector = Icons.Default.Thermostat, contentDescription = "")
//                    Text("feels like $feelsLike")
                    Text(stringResource(R.string.feels_like, feelsLike.formatNumberBasedOnLanguage(CURRENT_LANG)))
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = ""
            )

            Text(stringResource(R.string.last_updated, "${date}, ${formattedTime}"), fontSize = 12.sp)
        }

    }
}