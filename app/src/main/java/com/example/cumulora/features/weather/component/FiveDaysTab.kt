package com.example.cumulora.features.weather.component

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


@Composable
fun FiveDaysTab() {
    val daysList = listOf("Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri")
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column {
            daysList.forEach {
                ForecastItem(day = it)
            }
        }
    }
}

@Composable
fun ForecastItem(day: String) {
    ListItem(colors = ListItemDefaults.colors(containerColor = Color.Cyan.copy(alpha = 0.2f)),
        headlineContent = {
            Text(day)
        },
        supportingContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) { Text("30/Mar - Cloudy") }
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.scattered_clouds), contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text("24° / 22°")
            }
        })
}