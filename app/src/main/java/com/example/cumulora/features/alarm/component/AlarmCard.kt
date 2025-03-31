package com.example.cumulora.features.alarm.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cumulora.data.models.alarm.Alarm


@Composable
fun AlarmCard(alarm: Alarm, showDivider : Boolean = true) {


    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text("Time")
        Switch(checked = false, onCheckedChange = {})
    }
    if (showDivider){
        HorizontalDivider(Modifier.padding(vertical = 16.dp))
    }
}