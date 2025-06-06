package com.example.cumulora.features.alarm

import AlarmViewModel
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlarm
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cumulora.R
import com.example.cumulora.core.factories.AlarmViewModelFactory
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.responsestate.AlarmStateResponse
import com.example.cumulora.features.alarm.component.AlarmCard
import com.example.cumulora.features.alarm.manager.AlarmSchedulerImpl
import com.example.cumulora.features.weather.component.darken
import com.example.cumulora.ui.component.SwipeToDeleteContainer
import com.example.cumulora.utils.repoInstance
import java.time.LocalTime


val TAG = "ALARM_DIALOG"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Composable
fun AlarmScreenUI(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState, cityName: String) {


    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val alarmScheduler = remember { AlarmSchedulerImpl(context) }
    val viewModel: AlarmViewModel = viewModel(factory = AlarmViewModelFactory(repoInstance(context)))
    val alarmsState by viewModel.alarmsState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier, floatingActionButton = {
        FloatingActionButton(

            containerColor = MaterialTheme.colorScheme.primaryContainer.darken(-0.4f),
            onClick = {
            showDialog = true
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

            is AlarmStateResponse.Failure -> {
                NoData()
            }
            is AlarmStateResponse.Success -> {

                val alarms = (alarmsState as AlarmStateResponse.Success).data

                if (alarms.isEmpty()) {
                    NoData()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(
                            end = 16.dp,
                            start = 16.dp,
                            top = 16.dp,
                            bottom = 84.dp
                        )
                    ) {
                        items(alarms, key = { it.id }) {
                            SwipeToDeleteContainer(
                                item = it,
                                onDelete = { viewModel.deleteAlarm(it) },
                                onRestore = { },
                                snackBarHostState = snackbarHostState,
                                snackBarString = it.cityName + context.getString(R.string.alarm_)+ " ",
                                content = {
                                    AlarmCard(alarm = it)
                                }
                            )
                        }
                    }
                }
            }
        }

    }


    AlarmSetupDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = { time, duration, label ->
            val alarm = Alarm(
                id = System.currentTimeMillis().toInt(),
                label = label,
                time = time,
                cityName = cityName,
                duration = duration
            )
            Log.d(TAG, "AlarmScreenUI: ${alarm.id} ${alarm.time} ${alarm.duration} ${alarm.label}")

            viewModel.addAlarm(alarm)

            alarmScheduler.schedulerAlarm(alarm)

            showDialog = false
        })
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmSetupDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (LocalTime, Int, String) -> Unit
) {

    var isSeconds by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }
    var calcDuration by remember { mutableStateOf(if (duration.isEmpty()) 50 else duration.toInt()) }
    val scrollState by remember { mutableStateOf(ScrollState(0)) }

    val timeState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = false
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = stringResource(R.string.set_alarm), fontWeight = FontWeight.Bold) },
            text = {
                Column(
                    Modifier
                        .verticalScroll(scrollState)
                        .imePadding()
                ) {
                    TimeInput(state = timeState)
                    TextField(value = label, onValueChange = { label = it }, label = { Text(stringResource(R.string.alarm_label)) })
                    Spacer(Modifier.height(16.dp))
                    Row(
                        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement =
                        Arrangement.SpaceBetween
                    ) {
                        TextField(modifier = Modifier.weight(1f), value = duration, onValueChange =
                        { duration = it }, label = {
                            Text(stringResource(R.string.duration))
                        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(Modifier.width(16.dp))
                        Row {
                            DurationType(isSelected = !isSeconds, label = stringResource(R.string.minutes)) {
                                isSeconds = false
                                calcDuration = if(duration.isEmpty()) 50 else duration.toInt() * 60
                            }
                            Spacer(Modifier.width(16.dp))
                            DurationType(isSelected = isSeconds, label = stringResource(R.string.seconds)) {
                                isSeconds = true
                                calcDuration = if(duration.isEmpty()) 50 else duration.toInt()
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime = LocalTime.of(timeState.hour, timeState.minute)
                        onConfirm(selectedTime, calcDuration, label)
                    }
                ) {
                    Text(stringResource(R.string.set_alarm),style = TextStyle(color = MaterialTheme.colorScheme.secondaryContainer.darken(-0.8f)))
                }
            },
            textContentColor = Color.White,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancel), style = TextStyle(color = MaterialTheme.colorScheme.secondaryContainer.darken(-0.8f)))
                }
            }
        )
    }
}

@Composable
private fun DurationType(isSelected: Boolean, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RadioButton(colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.secondaryContainer),selected = isSelected, onClick = onClick)
        Text(label)
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
        val lottie by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.alarm))

        val progress by animateLottieCompositionAsState(
            composition = lottie,
            restartOnPlay = true,
            speed = 1f,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
        )
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LottieAnimation(
            composition = lottie,
            progress = { progress },
            modifier = Modifier.size(200.dp)
        )
    }
}
