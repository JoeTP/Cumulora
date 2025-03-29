package com.example.cumulora.features.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cumulora.ui.theme.Purple
import com.example.cumulora.ui.theme.Purple40

@Composable
fun WeatherCard(
    title: String = "",
    trail: String = "",
    icon: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    composable: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = Purple.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 2.dp,
                color = Purple40.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                icon()
                Spacer(modifier.width(8.dp))

                if (title.isNotEmpty()) Text(title)
            }
                if (trail.isNotEmpty()) Text(trail)
        }
        Spacer(modifier.height(8.dp))
            composable()
    }
}