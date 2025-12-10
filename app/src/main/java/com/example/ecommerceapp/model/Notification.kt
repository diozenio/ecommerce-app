package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class NotificationCategory(val key: String) {
    OFFER("offer"),
    PAYMENT("payment"),
    PROFILE("profile")
}

@Entity(tableName = "notification")
data class Notification(
    @PrimaryKey(autoGenerate = false)
    override val id: Int = 0,
    val title: String,
    val subtitle: String,
    val category: NotificationCategory,
    val date: Long
) : Model