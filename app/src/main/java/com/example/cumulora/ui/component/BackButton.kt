package com.example.cumulora.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cumulora.utils.CURRENT_LANG


@Composable
fun BackButton(navController: NavController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = if (CURRENT_LANG == "en") Icons.Default.ArrowBack else Icons
                .Default.ArrowForward,
            contentDescription = "Back"
        )
    }
}