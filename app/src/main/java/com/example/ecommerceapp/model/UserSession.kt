package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_session_table")
data class UserSession(
    @PrimaryKey
    val id: String,
    val fullName: String,
    val email: String
)