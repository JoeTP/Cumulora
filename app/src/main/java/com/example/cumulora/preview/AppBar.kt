@file:OptIn(ExperimentalFoundationApi::class)

package com.example.cumulora.preview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun AppBarPreview() {
    val tabs = listOf("Current", "5 Days")
    var selectedTabIndex by remember { mutableStateOf(0) }
    var pagerState = rememberPagerState { tabs.size }
    LaunchedEffect(selectedTabIndex) { pagerState.scrollToPage(selectedTabIndex) }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) selectedTabIndex = pagerState.currentPage
    }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
        ) {
            //HERE U CALL THE CURRENT TAB AND 5 DAY TAB
            //but for now i using full size text
            //ماتنساش تخلي التوب يبقى column
            Text(text = tabs[it])
        }
    }

}