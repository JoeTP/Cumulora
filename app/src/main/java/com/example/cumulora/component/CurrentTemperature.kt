package com.example.cumulora.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun CurrentTemperature() {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = "Weather Information",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White,
            fontSize = 24.sp
        )
    }
}