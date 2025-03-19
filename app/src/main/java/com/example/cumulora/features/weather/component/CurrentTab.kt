package com.example.cumulora.features.weather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.component.OvalCard


@Composable
fun CurrentTab() {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(count = 15) { index ->
                OvalCard()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .height(400.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Humidity("30%")
            }
            item {
                Wind("30")
            }
            item {
                Pressure("20")
            }
            item {
                Clouds("30%")
            }
//            TODO "Extract data from viewmodel"

        }
    }
}

@Composable
private fun Wind(windSpeed: String) {
    WeatherCard("Wind Speed", R.drawable.wind) {
        Box(contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.compas), contentDescription = "")
            Image(
                painter = painterResource(id = R.drawable.compas_arrow), contentDescription = "",
                modifier = Modifier.rotate(36f)
            )
            Text(windSpeed)
//            TODO("DONT FORGET THE UNIT")
        }
    }
}

@Composable
private fun Humidity(humidity: String) {
    WeatherCard("Humidity", R.drawable.humidity) {
        Text(humidity)
    }
}

@Composable
private fun Pressure(pressure: String) {
    WeatherCard("Pressure", R.drawable.rain) {
        Text(pressure)
    }
}

@Composable
private fun Clouds(clouds: String) {
    WeatherCard("Clouds", R.drawable.cloud_icon) {
        Text(clouds)
    }
}
