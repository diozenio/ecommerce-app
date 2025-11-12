package com.example.ecommerceapp.data.notification

import androidx.room.TypeConverter
import com.example.ecommerceapp.model.NotificationCategory
import java.util.Date

class NotificationConverter {
    @TypeConverter
    fun fromCategory(category: NotificationCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(categoryName: String): NotificationCategory {
        return NotificationCategory.valueOf(categoryName)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}