import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.alarm.Alarm
import kotlinx.coroutines.launch

class AlarmViewModel() : ViewModel() {
    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
        }
    }

    fun getAlarms() {
        viewModelScope.launch {
        }
    }
}