package com.example.cumulora.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cumulora.navigation.ScreenRoutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController: NavHostController) {

//    val navController = rememberNavController()

    TopAppBar(title = {}, actions = {
        IconButton(onClick = {navController.navigate(ScreenRoutes.Alarm)}) {
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription = "Alarm"
            )
        }
//        IconButton(onClick = {}) {
//            Icon(
//                imageVector = Icons.Default.Notifications,
//                contentDescription = "Notification"
//            )
//        }
        IconButton(onClick = {navController.navigate(ScreenRoutes.Settings)}) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings"
            )
        }
    })
}