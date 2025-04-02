package com.example.cumulora.features.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.SettingsViewModelFactory
import com.example.cumulora.features.settings.component.ListTile
import com.example.cumulora.ui.theme.LighterCyan
import com.example.cumulora.utils.CURRENT_LANG
import com.example.cumulora.utils.getTempUnitSymbol
import com.example.cumulora.utils.getTemperatureUnit
import com.example.cumulora.utils.isLocationEnabled
import com.example.cumulora.utils.isLocationPermissionGranted
import com.example.cumulora.utils.repoInstance
import com.example.cumulora.utils.requestLocationSettings
import com.example.cumulora.utils.restartActivity
import com.example.cumulora.utils.waitForLocationUpdates
import java.util.Locale

val TAG = "SettingsScreenUI"

@Composable
fun SettingsScreenUI(modifier: Modifier = Modifier, onNavigateToMap: () -> Unit) {
    val ctx = LocalContext.current
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(repoInstance(ctx)))
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()
    val langOptions = listOf("en", "ar")
    val locationOptions = listOf("my location", "custom")
    val unitOptions =
        listOf(stringResource(R.string.c_m_s), stringResource(R.string.f_m_s), stringResource(R.string.k_m_h))
    var showDialog by remember { mutableStateOf(false) }
    var showPermissionDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            if (!isLocationEnabled(ctx)) {
                showDialog = true
            }
        } else {
            showPermissionDialog = true
        }
    }


    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        ListTile(stringResource(R.string.language), Icons.Outlined.Language) {

            SingleChoiceSegmentedButton(
                options = langOptions,
                currentSelected = langOptions.indexOf(settingsState.lang)
            ) {
                if (CURRENT_LANG != langOptions[it]) {
                    viewModel.changeLang(langOptions[it])
                    restartActivity(ctx)
                }
            }
        }
        CustomDivider()

        ListTile(stringResource(R.string.location), Icons.Outlined.Map) {
            SingleChoiceSegmentedButton(
                options = locationOptions,
                currentSelected = locationOptions.indexOf(settingsState.locationType)
            ) {
                when {
                    locationOptions[it] == locationOptions.last() -> {
                        onNavigateToMap()
                    }

                    !isLocationPermissionGranted(ctx) -> {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }

                    else -> {
                        requestLocationSettings(ctx as Activity) { isLocationEnabled ->
                            if (isLocationEnabled) {
                                waitForLocationUpdates(ctx) { latitude, longitude ->
                                    Log.d(TAG, "SettingsScreenUI: $latitude, $longitude")
                                    viewModel.useUserLocation(latitude.toString(), longitude.toString())
                                    viewModel.changeLocationType(locationOptions[it])
                                }
                            }
                        }
                    }
                }
            }
        }

        CustomDivider()

        ListTile(stringResource(R.string.units), Icons.Outlined.Straighten) {}
        SingleChoiceSegmentedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            options = unitOptions,
            currentSelected = unitOptions.indexOf(ctx.getTempUnitSymbol(settingsState.unit))
        ) {
            viewModel.changeUnit(getTemperatureUnit(unitOptions[it]))
        }
    }
    CustomDivider()
}


@Composable
fun SingleChoiceSegmentedButton(
    modifier: Modifier = Modifier,
    currentSelected: Int,
    options: List<String>,
    onClick: (Int) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(if (currentSelected < 0) 0 else currentSelected) }

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = LighterCyan.copy
                        (alpha = 0.4f)
                ),
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



