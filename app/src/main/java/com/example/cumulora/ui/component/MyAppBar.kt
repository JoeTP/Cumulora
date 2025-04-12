package com.example.cumulora.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.cumulora.navigation.ScreenRoutes
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.formatNumberBasedOnLanguage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    navController: NavController,
//    bgColor: Color,
    cityName: String,
    currentTemp: String,
    scrollState: ScrollState,

    ) {

    val surfaceColor = MaterialTheme.colorScheme.secondaryContainer

    val scrollProgress by remember(scrollState.value) {
        derivedStateOf {
            minOf(scrollState.value / 500f, 1f)
        }
    }
    val titleAlpha by animateFloatAsState(
        targetValue = if (scrollProgress < 0.8f) 0f else scrollProgress,
        label = "titleAlpha"
    )

    val topBarColor = remember(scrollProgress) {
        if (scrollProgress < 0.5f) {
            Color.Transparent
        } else {
            surfaceColor.copy(alpha = (scrollProgress - 0.5f) * 1.8f)
        }
    }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .alpha(titleAlpha)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentTemp.formatNumberBasedOnLanguage(CURRENT_LANG),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = titleAlpha)
                )
                Text(
                    text = cityName,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = titleAlpha)
                )
                Text("")
            }
        },
        actions = {
            IconButton(
                onClick = { navController.navigate(ScreenRoutes.Settings) },
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topBarColor,
//            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = titleAlpha),
            actionIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = titleAlpha),
            titleContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = titleAlpha)
        )
    )
}