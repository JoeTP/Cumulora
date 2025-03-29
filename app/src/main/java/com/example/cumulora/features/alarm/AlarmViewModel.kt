import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cumulora.data.models.alarm.Alarm
import com.example.cumulora.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class AlarmViewModel(private val repo: WeatherRepository) : ViewModel() {


    fun addAlarm(alarm: Alarm) {
        viewModelScope.launch {
            repo.addAlarm(alarm)

        }
    }

    fun getAlarms() {
        viewModelScope.launch {
//            repo.getAlarms()
        }
    }
}

class AlarmViewModelFactory(private val repo: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}