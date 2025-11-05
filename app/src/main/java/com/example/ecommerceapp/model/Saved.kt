package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
data class SavedItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val product: Product,
)
