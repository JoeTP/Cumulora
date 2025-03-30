package com.example.cumulora.ui.component

import androidx.compose.foundation.ScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cumulora.navigation.ScreenRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController: NavController, bgColor: Color) {


    TopAppBar(title = {},
        actions = {
        IconButton(onClick = {navController.navigate(ScreenRoutes.Settings)}) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = bgColor,
            scrolledContainerColor = Color.White,
        ),
    )
}