package com.example.cumulora.features.alarm

import AlarmViewModel
import AlarmViewModelFactory
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.features.alarm.component.AlarmCard
import com.example.cumulora.manager.AlarmSchedulerImpl
import com.example.cumulora.utils.repoInstance
import java.time.LocalTime


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun AlarmScreenUI(modifier: Modifier = Modifier) {

    var showDialog by remember { mutableStateOf(false) }
    var context = LocalContext.current
    val alarmScheduler = remember { AlarmSchedulerImpl(context) }
    val viewModel : AlarmViewModel = viewModel(factory = AlarmViewModelFactory(repoInstance(context)))

    var c = 1

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(onClick = {
//            showDialog = true
            val alarm = Alarm(id = c, time = LocalTime.now().plusSeconds(3), label = "Alarm", duration = 15)
            viewModel.addAlarm(alarm)
            alarmScheduler.schedulerAlarm(alarm)
            c++
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


/*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlarmScreen() {
    val viewModel: AlarmViewModel = viewModel()
    val context = LocalContext.current
    val alarmScheduler = remember { AlarmSchedulerImpl(context) }
    var alarmTime by remember { mutableStateOf(LocalTime.now()) }
    var alarmLabel by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Time picker
        Text(text = "Set Alarm Time", style = MaterialTheme.typography.h6)
        TimePicker(
            time = alarmTime,
            onTimeChange = { alarmTime = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Label input
        OutlinedTextField(
            value = alarmLabel,
            onValueChange = { alarmLabel = it },
            label = { Text("Alarm Label") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Set alarm button
        Button(
            onClick = {
                val newAlarm = Alarm(
                    id = Random.nextInt(1000, 9999), // Generate random ID
                    label = alarmLabel,
                    time = alarmTime,
                    duration = 0 // Set your duration as needed
                )

                // Schedule the alarm
                alarmScheduler.schedulerAlarm(newAlarm)

                // Optionally save to your repository
                viewModel.addAlarm(newAlarm)

                // Show confirmation
                Toast.makeText(context, "Alarm set!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Alarm")
        }

        // List of active alarms
        AlarmList(viewModel = viewModel, alarmScheduler = alarmScheduler)
    }
}

@Composable
fun TimePicker(
    time: LocalTime,
    onTimeChange: (LocalTime) -> Unit
) {
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = time.hour,
        initialMinute = time.minute
    )

    TextButton(
        onClick = { showTimePicker = true }
    ) {
        Text(
            text = DateTimeFormatter.ofPattern("HH:mm").format(time),
            style = MaterialTheme.typography.h4
        )
    }

    if (showTimePicker) {
        TimePickerDialog(
            onCancel = { showTimePicker = false },
            onConfirm = {
                onTimeChange(
                    LocalTime.of(timePickerState.hour, timePickerState.minute)
                )
                showTimePicker = false
            },
            state = timePickerState
        )
    }
}

@Composable
fun AlarmList(
    viewModel: AlarmViewModel,
    alarmScheduler: AlarmScheduler
) {
    val alarms by viewModel.alarms.collectAsState(initial = emptyList())

    LazyColumn {
        items(alarms) { alarm ->
            AlarmItem(
                alarm = alarm,
                onDelete = {
                    alarmScheduler.cancelAlarm(alarm)
                    viewModel.deleteAlarm(alarm)
                }
            )
        }
    }
}

@Composable
fun AlarmItem(
    alarm: Alarm,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = alarm.label,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = DateTimeFormatter.ofPattern("HH:mm").format(alarm.time),
                    style = MaterialTheme.typography.body1
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete alarm")
            }
        }
    }
}*/
