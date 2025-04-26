@file:OptIn(ExperimentalFoundationApi::class)

package com.example.cumulora.features.weather.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.SystemUpdateAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity

//@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetailsSection(
    weather: WeatherEntity,
    forecast: ForecastResponse,
    forecastFiveDays: List<Forecast>,
    tempUnit: String,
    windUnit: String,
    scrollState: ScrollState,
) {
    val tabs = listOf(stringResource(R.string.today), stringResource(R.string._5_days))
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState { tabs.size }

    val scrollProgress by remember(scrollState.value) {
        derivedStateOf {
            minOf(scrollState.value / 500f, 1f)
        }
    }

    val detailsBgAlpha = remember(scrollProgress) {
        0.4f + (scrollProgress * (0.4f - 0.15f))
    }

    val surfaceColor = MaterialTheme.colorScheme.surface

    val bgColor = surfaceColor.copy(alpha = detailsBgAlpha)

    val ovalColor = MaterialTheme.colorScheme.primary.copy(alpha = detailsBgAlpha)


    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    val shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clip(shape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
//                        Color.Red,
//                        bgColor.copy(alpha = -0.1f),
                        bgColor,
                        bgColor.darken(0.1f),
                        bgColor.darken(0.5f).copy(alpha = 0.7f)
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .border(
                shape = shape,
                width = 1.dp,
                color = Color.White.copy(alpha = 0.2f)
            )
    ) {
        WeatherDetailsSectionChild(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            pagerState = pagerState,
            weather = weather,
            forecast = forecast,
            forecastFiveDays = forecastFiveDays,
            tempUnit = tempUnit,
            windUnit = windUnit,
            ovalColor = ovalColor,
            onTabSelected = { index -> selectedTabIndex = index }
        )
    }
}

@Composable
private fun WeatherDetailsSectionChild(
    tabs: List<String>,
    selectedTabIndex: Int,
    pagerState: PagerState,
    weather: WeatherEntity,
    forecast: ForecastResponse,
    forecastFiveDays: List<Forecast>,
    tempUnit: String,
    windUnit: String,
    ovalColor: Color,
    onTabSelected: (index: Int) -> Unit
) {
    val tabIcons = listOf(Icons.Default.SystemUpdateAlt, Icons.Default.CalendarMonth)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .width(50.dp)
                    .height(4.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),
            ) {}
        }
        TabRow(
            modifier = Modifier.background(Color.Transparent),
            containerColor = Color.Transparent,
            contentColor =
            MaterialTheme.colorScheme.secondaryContainer.darken(-1f),
            selectedTabIndex = selectedTabIndex,
            divider = {},
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .fillMaxWidth()
                        .height(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.primaryContainer,
                                        MaterialTheme.colorScheme.primaryContainer,
                                        Color.Transparent
                                    ),
                                    startX = 0f,
                                    endX = Float.POSITIVE_INFINITY
                                ),
//                                blendMode = BlendMode.DstIn
                            )
                    )
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier.background(Color.Transparent),
                    icon = { Icon(imageVector = tabIcons[index], contentDescription = null) },
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = title) }
                )
            }
        }


        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(bottom = 60.dp)
        ) { page ->
            when (page) {
                0 -> TodayTab(weather, forecast, tempUnit = tempUnit, windUnit = windUnit, ovalColor = ovalColor)
                1 -> FiveDaysTab(forecastFiveDays, tempUnit)
            }
        }
    }
}

fun Color.darken(factor: Float): Color {
    return this.copy(red = red * (1 - factor), green = green * (1 - factor), blue = blue * (1 - factor))
}