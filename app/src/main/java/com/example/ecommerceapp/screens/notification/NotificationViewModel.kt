import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.repository.NotificationRepository
import com.example.ecommerceapp.model.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class NotificationUiState(
    val notifications: List<Notification> = emptyList(),
    val groupedNotifications: Map<String, List<Notification>> = emptyMap(),
    val isLoading: Boolean = false
)

class NotificationViewModel(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val notifications = repository.findAll()

            println(notifications)

            val grouped = groupNotificationsByDate(notifications)

            _uiState.update { currentState ->
                currentState.copy(
                    notifications = notifications,
                    groupedNotifications = grouped,
                    isLoading = false
                )
            }
        }
    }

    private fun groupNotificationsByDate(notifications: List<Notification>): Map<String, List<Notification>> {
        val today = Calendar.getInstance()

        return notifications
            .sortedByDescending { it.date }
            .groupBy { notification ->
                val notificationCalendar = Calendar.getInstance().apply { time = Date(notification.date) }

                if (isSameDay(today, notificationCalendar)) {
                    "Today"
                } else {
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(notification.date)
                }
            }
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    class Factory(private val repository: NotificationRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotificationViewModel(repository) as T
        }
    }
}