package com.example.cumulora.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cumulora.utils.weatherIcons

@Composable
fun OvalCard() {
    Surface(shape = CircleShape) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .width(60.dp)
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("12 AM")
            Image(
                painter = painterResource(id = weatherIcons.getValue("01d")),
                contentDescription = ""
            )
            Text("12°C")
        }
    }
}