package com.example.cumulora.features.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun WeatherCard(
    title: String, iconId: Int, color: Color = Color.DarkGray,
    composable: @Composable
        () ->
    Unit,
) {

    Surface(shape = RoundedCornerShape(20.dp), color = color) {
        Column(modifier = Modifier
            .padding(8.dp)
            .height(160.dp)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Image(
                    modifier = Modifier.size(24.dp), painter = painterResource(id = iconId),
                    contentDescription = ""
                )
                Spacer(Modifier.width(8.dp))
                Text(title)
            }
            Spacer(Modifier.height(8.dp))

            Surface(color = Color.Transparent, modifier = Modifier.fillMaxSize()) {
                composable()
            }
//            Box (modifier = Modifier.fillMaxSize()){
//            }
        }
    }

}