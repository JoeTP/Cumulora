package com.example.cumulora.features.savedweather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.data.local.SavedWeather

@Composable
fun SavedWeatherCard(savedWeather: SavedWeather) {

    Row(
        Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(savedWeather.cityName)
        }
        Text(savedWeather.weatherResponse?.main?.temp.toString())
        Icon(painter = painterResource(R.drawable.scattered_clouds), contentDescription = "")
    }

}