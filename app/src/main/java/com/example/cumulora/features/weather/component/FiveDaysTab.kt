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
import com.example.cumulora.R
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastEntity
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.utils.weatherIcons
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FiveDaysTab(forecastFiveDays: List<Forecast>) {

    val daysList = listOf("Sat", "Sun", "Mon", "Tue", "Wed",)

//    val data = forecast.asFlow().filter { it.dtTxt. }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            daysList.forEachIndexed { index, s ->
                ForecastItem(day = s, forecastFiveDays[index])
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItem(day: String, forecast: Forecast) {
    ListItem(colors = ListItemDefaults.colors(containerColor = Color.Cyan.copy(alpha = 0.2f)),
        headlineContent = {
            Text(day)
        },
        supportingContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) { Text("${formatDateToDdMmm(forecast.dtTxt)} - ${forecast.weather.first().description}") }
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = weatherIcons.getValue(forecast.weather.first().icon)),
                    contentDescription
                = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text("${forecast.main.tempMax} / ${forecast.main.tempMin}")
            }
        })
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToDdMmm(dateTimeString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)

    val outputFormatter = DateTimeFormatter.ofPattern("dd-MMM")
    return dateTime.format(outputFormatter)
}