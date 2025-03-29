package com.example.cumulora.features.alarm

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.alarm.component.AlarmCard
import java.time.LocalTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun AlarmScreenUI(modifier: Modifier = Modifier) {

    var showDialog by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(onClick = {
//            showDialog = true
            val alarm = Alarm(id = 0, time = LocalTime.now(), label = "Alarm", duration = 5)
            scheduleAlarmWork(alarm, context){

            }
        }) {
            Icon(
                imageVector = Icons.Default.AddAlarm,
                contentDescription = "Add Alarm"
            )
        }
    }) {
        LazyColumn(contentPadding = PaddingValues(end = 16.dp, start = 16.dp, top = 16.dp, bottom = 84.dp)) {
            items(15) {
                AlarmCard(showDivider = it != 14)
            }
        }
    }


    AlarmSetupDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = { showDialog = false })
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSetupDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit
) {
    val timeState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = false
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Set Alarm", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Select alarm time:")
                    Spacer(modifier = Modifier.height(16.dp))
                    TimePicker(state = timeState)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime = LocalTime.of(timeState.hour, timeState.minute)
                        onConfirm(selectedTime)
                    }
                ) {
                    Text("Set Alarm")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
