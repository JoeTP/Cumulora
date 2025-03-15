package com.example.cumulora.features.onboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun OnBoardingScreenUI(onNavigateToHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp), horizontalAlignment = Alignment
            .CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Surface(modifier = Modifier.weight(1f)) {
            //Here will put image
        }
        Text("Welcome to Cumulora")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNavigateToHome) {
            Text("Start")
        }
    }
}