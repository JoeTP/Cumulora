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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CurrentTemperature() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("CityName")
        Row (modifier = Modifier.background(Color.Gray), verticalAlignment = Alignment.Top){
            Text(
                text = "19°",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 54.sp
            )
            Text("C")
        }
            Text("Feels like 19°")
            Text("Description(clear)")
            Text("Date & Time")
    }
}