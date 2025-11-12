package com.example.ecommerceapp.data.notification

import com.example.ecommerceapp.data.core.BaseManager
import com.example.ecommerceapp.model.Notification
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NotificationManager(
    dao: NotificationDao,
) : BaseManager<Notification>(dao) {
    fun groupNotificationsByDate(notifications: List<Notification>): Map<String, List<Notification>> {

        val today = Calendar.getInstance()

        return notifications
            .sortedByDescending { it.date }
            .groupBy { notification ->
                val notificationCalendar = Calendar.getInstance().apply { time = notification.date }

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
}