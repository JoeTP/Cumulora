package com.example.cumulora.features.weather.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.R
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage
import com.example.cumulora.utils.weatherIcons

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentTemperature(
    cityName: String,
    temperature: Double,
    description: String,
    time: String,
    date: String,
    icon: String,
    tempUnit: String,
    onMapNavigate: () -> Unit
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Column(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onMapNavigate() }
                    .padding(12.dp),
                text = cityName,
                maxLines = 2,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )
            Row(verticalAlignment = Alignment.Top) {
                Text(
                    text = temperature.toInt().toString().formatNumberBasedOnLanguage(CURRENT_LANG),
                    textAlign = TextAlign.Center,
                    fontSize = 54.sp
                )
                Text(tempUnit)

            }
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = weatherIcons.getValue(icon)),
                contentDescription = ""
            )
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(painter = painterResource(id = R.drawable.temp_feel_like), contentDescription = "Map")
//                Spacer(Modifier.width(8.dp))
//                Text(
//                    stringResource(
//                        R.string.feels_like,
//                        feelsLike.toString().formatNumberBasedOnLanguage(CURRENT_LANG)
//                    )
//                )
//            }
            Text(description)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = ""
            )
            Text(stringResource(R.string.last_updated, "${date} - ${time}"), fontSize = 12.sp)
        }

    }
}