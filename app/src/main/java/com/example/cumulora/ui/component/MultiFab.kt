package com.example.cumulora.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cumulora.navigation.ScreenRoutes
import com.example.cumulora.ui.theme.LightLinColorOne
import com.example.cumulora.ui.theme.LightRed
import com.example.cumulora.ui.theme.Red

@Composable
fun MultiFab(navController: NavController, cityName: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 140f else 0f,
        label = "fabRotation"
    )

    Column (horizontalAlignment = Alignment.CenterHorizontally){
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            FloatingActionButton(
                containerColor = LightRed,
                onClick = { navController.navigate(ScreenRoutes.SavedWeather)},
                modifier = Modifier.padding(bottom = 8.dp).size(46.dp)
            ) {
                Icon(Icons.Default.Favorite, "Favorite", tint = Color.White)
            }
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            FloatingActionButton(
                containerColor = LightLinColorOne,
                onClick = { navController.navigate(ScreenRoutes.Alarm(cityName)) },
                modifier = Modifier.padding(bottom = 8.dp).size(46.dp)
            ) {
                Icon(Icons.Default.Alarm, "Alarm")
            }
        }

        FloatingActionButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Menu",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}