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
}