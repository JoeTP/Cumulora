import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.features.alarm.AlarmStateResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlarmViewModel(private val repo: WeatherRepository) : ViewModel() {

    private val _mutableAlarmsState = MutableStateFlow<AlarmStateResponse>(AlarmStateResponse.Loading)
    val alarmsState = _mutableAlarmsState.asStateFlow()

    init {
        getAlarms()
    }

    fun addAlarm(alarm: Alarm) = viewModelScope.launch {
        try {
            repo.addAlarm(alarm)
            getAlarms()
        } catch (e: Exception) {
            _mutableAlarmsState.value = AlarmStateResponse.Failure(e.message ?: "Unknown error")
        }
    }

    private fun getAlarms() = viewModelScope.launch {
        try {
            repo.getAlarms().collect {
                _mutableAlarmsState.value = AlarmStateResponse.Success(it)
            }
        } catch (e: Exception) {
            _mutableAlarmsState.value = AlarmStateResponse.Failure(e.message ?: "Unknown error")
        }
    }

    fun deleteAlarm(alarm: Alarm) = viewModelScope.launch {
        try {
            repo.deleteAlarm(alarm)
            getAlarms()
        } catch (e: Exception) {
            _mutableAlarmsState.value = AlarmStateResponse.Failure(e.message ?: "Unknown error")
        }
    }
}


