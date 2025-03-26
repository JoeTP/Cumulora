package com.example.cumulora.features.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.SettingsViewModelFactory
import com.example.cumulora.features.settings.component.ListTile
import com.example.cumulora.utils.repoInstance
import java.util.Locale

@Composable
fun SettingsScreenUI(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(repoInstance(ctx)))
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle(initialValue = SettingsState())

    //TODO: Get from enum
    val langOptions = listOf("en", "ar")
    val locationOptions = listOf("my location", "custom")
    val unitOptions = listOf("metric", "imperial", "standard")

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ListTile(stringResource(R.string.language), Icons.Outlined.Language)
        SingleChoiceSegmentedButton(
            options = langOptions,
            currentSelected = langOptions.indexOf(settingsState.lang)
        ) {
            viewModel.changeLang(langOptions[it])
        }

        CustomDivider()

        ListTile(stringResource(R.string.location), Icons.Outlined.Map)
        SingleChoiceSegmentedButton(
            options = locationOptions,
            currentSelected = locationOptions.indexOf(settingsState.locationType)
        ) {
            viewModel.changeLocationType(locationOptions[it])
        }

        CustomDivider()

        ListTile(stringResource(R.string.units), Icons.Outlined.Straighten)
        SingleChoiceSegmentedButton(
            options = unitOptions,
            currentSelected = unitOptions.indexOf(settingsState.unit)
        ) {
            viewModel.changeUnit(unitOptions[it])
        }

        CustomDivider()
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    currentSelected: Int = 0,
    options: List<String>,
    onClick: (Int) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(if (currentSelected < 0) 0 else currentSelected) }

    LaunchedEffect(currentSelected) {
        selectedIndex = if (currentSelected < 0) 0 else currentSelected
    }

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                icon = {},
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    onClick(index)
                },
                selected = index == selectedIndex,
                label = {
                    val newLabel = label.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                    }
                    Text(newLabel)
                }
            )
        }
    }
}
@Composable
fun CustomDivider() = HorizontalDivider(Modifier.padding(bottom = 10.dp, top = 20.dp))
