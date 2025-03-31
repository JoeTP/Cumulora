package com.example.cumulora.features.alarm

import AlarmViewModel
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.R
import com.example.cumulora.core.factories.AlarmViewModelFactory
import com.example.cumulora.features.alarm.component.AlarmCard
import com.example.cumulora.manager.AlarmSchedulerImpl
import com.example.cumulora.utils.repoInstance
import java.time.LocalTime
import java.time.temporal.ChronoUnit


val TAG = "ALARM_DIALOG"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun AlarmScreenUI(modifier: Modifier = Modifier) {



    var showDialog by remember { mutableStateOf(false) }
    var context = LocalContext.current
    val alarmScheduler = remember { AlarmSchedulerImpl(context) }
    val viewModel: AlarmViewModel = viewModel(factory = AlarmViewModelFactory(repoInstance(context)))
    val alarmsState by viewModel.alarmsState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(onClick = {
            showDialog = true
//            val alarm = Alarm(id = c, time = LocalTime.now().plusSeconds(3), label = "Alarm", duration = 15)

        }) {
            Icon(
                imageVector = Icons.Default.AddAlarm,
                contentDescription = "Add Alarm"
            )
        }
    }) {

        when (alarmsState) {
            is AlarmStateResponse.Loading -> {
                LoadingData()
            }

            is AlarmStateResponse.Failure -> {}
            is AlarmStateResponse.Success -> {

                val alarms = (alarmsState as AlarmStateResponse.Success).data

                if (alarms.isEmpty()) {
                    NoData()
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            end = 16.dp,
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 84.dp
                        )
                    ) {
                        items(alarms.size) {
                            AlarmCard(alarms[it], showDivider = it != alarms.size - 1)
                        }
                    }
                }
            }
        }

    }


    AlarmSetupDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {time ->
//            val x = time.truncatedTo( ChronoUnit.MINUTES)
//            Log.i(TAG, "AlarmScreenUI: $time ---- $x --- ${LocalTime.now()}")
            //            viewModel.addAlarm(alarm)
//            alarmScheduler.schedulerAlarm(alarm)
            showDialog = false
        })
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
                    TimePicker(state = timeState)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime = LocalTime.of(timeState.hour, timeState.minute)
                        onConfirm(selectedTime)
                    }
                ) {
                    Text(stringResource(R.string.set_alarm))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@Composable
fun LoadingData() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
fun NoData() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        //TODO FIX THIS LATER WITH LOTTIE
        Text("NO DATA")
    }
}
