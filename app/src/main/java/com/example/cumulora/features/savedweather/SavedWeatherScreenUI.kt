package com.example.cumulora.features.savedweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cumulora.features.savedweather.component.SavedWeatherCard


@Composable
fun SavedWeatherScreenUI(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement =
    Arrangement
        .spacedBy(16.dp)
        ,) {
        items(5) {
            SavedWeatherCard()
        }
    }
}