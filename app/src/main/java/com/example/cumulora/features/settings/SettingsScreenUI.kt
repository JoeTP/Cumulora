package com.example.cumulora.features.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.cumulora.features.settings.component.ListTile

@SuppressLint("RememberReturnType")
@Composable
fun SettingsScreenUI(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        ListTile("Use my current location", Icons.Outlined.Map) {
            Switch(
                checked = true, onCheckedChange = null
            )
        }
        ListTile("Language", Icons.Outlined.Language) {}
        ListTile("Units", Icons.Outlined.Straighten) {}
    }
}