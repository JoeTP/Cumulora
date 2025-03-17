package com.example.cumulora.features.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cumulora.component.CurrentTemperature
import com.example.cumulora.component.WeatherDetailsSection

@Preview
@Composable
fun WeatherScreenUI(onNavigateToAlarm: () -> Unit = {}, onNavigateToSavedWeather: () -> Unit = {}) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        Surface(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
            ,color = Color.Red
        ) {
            CurrentTemperature()
        }

        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
        ) {
            WeatherDetailsSection()
        }
    }
}