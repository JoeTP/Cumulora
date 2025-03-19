package com.example.cumulora.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CurrentTemperature(cityName: String, temperature: Double, feelsLike: Double, description: String, date:
String, time: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(cityName)
        Row (modifier = Modifier.background(Color.Gray), verticalAlignment = Alignment.Top){
            Text(
                text = "$temperature",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 54.sp
            )
            Text("C")
        }
            Text("$feelsLike°")
            Text(description)
            Text("$date    $time")
    }
}