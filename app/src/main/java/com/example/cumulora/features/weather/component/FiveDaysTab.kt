package com.example.cumulora.features.weather.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.utils.weatherIcons
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FiveDaysTab(forecastFiveDays: List<Forecast>) {

    val daysList = listOf("Sat", "Sun", "Mon", "Tue", "Wed")

//    val data = forecast.asFlow().filter { it.dtTxt. }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            for (i in 0..4) {
                ForecastItem(forecastFiveDays[i])
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItem(forecast: Forecast) {
    ListItem(colors = ListItemDefaults.colors(containerColor = Color.Cyan.copy(alpha = 0.2f)),
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
                Image(
                    painter = painterResource(id = weatherIcons.getValue(forecast.weather.first().icon)),
                    contentDescription
                    = ""
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.End) {
                    Text("${forecast.main.tempMax} / ${forecast.main.tempMin}")
                    Text(forecast.weather.first().description)
                }
            }
        })
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToDdMmm(dateTimeString: String): Pair<String, String> {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    val dateFormatter = DateTimeFormatter.ofPattern("dd-MMM")
    val formattedDate = dateTime.format(dateFormatter)

    val dayName = dateTime.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

    return Pair<String, String>(dayName, formattedDate)
}