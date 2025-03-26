@file:OptIn(ExperimentalFoundationApi::class)

package com.example.cumulora.features.weather.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    forecastFiveDays: List<Forecast>
) {
    val tabs = listOf(stringResource(R.string.today), stringResource(R.string._5_days))
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState { tabs.size }


    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Surface(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp),
        color = Color.LightGray
    ) {
        WeatherDetailsSectionChild(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            pagerState = pagerState,
            weather = weather,
            forecast = forecast,
            forecastFiveDays = forecastFiveDays,
            onTabSelected = { index -> selectedTabIndex = index }
        )
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherDetailsSectionChild(
    tabs: List<String>,
    selectedTabIndex: Int,
    pagerState: PagerState,
    weather: WeatherEntity,
    forecast: ForecastResponse,
    forecastFiveDays: List<Forecast>,
    onTabSelected: (index: Int) -> Unit
) {
    val tabIcons = listOf(Icons.Default.SystemUpdateAlt, Icons.Default.CalendarMonth)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .width(50.dp)
                    .height(4.dp),
                shape = CircleShape,
                color = Color.Black,
            ) {}
        }
        Surface(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        icon = { Icon(imageVector = tabIcons[index], contentDescription = null) },
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        // HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) { page ->
            when (page) {
                0 -> CurrentTab(weather, forecast)
                1 -> FiveDaysTab(forecastFiveDays)
            }
        }
    }
}
