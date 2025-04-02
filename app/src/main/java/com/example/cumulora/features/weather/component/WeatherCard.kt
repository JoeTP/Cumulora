package com.example.cumulora.features.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.ui.theme.Purple
import com.example.cumulora.ui.theme.Purple40

@Composable
fun WeatherCard(
    title: String = "",
    subtitle: String = "",
    icon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    composable: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondaryContainer.copy(0.2f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    icon()
                    Spacer(Modifier.width(8.dp))

                    if (title.isNotEmpty()) Text(title)
                }
            if (subtitle.isNotEmpty()) Text(subtitle, fontSize = 12.sp)
            }
        }
        Spacer(Modifier.height(8.dp))
        composable()
    }
}