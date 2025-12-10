package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class OrderStatus {
    PACKING,
    PICKED,
    IN_TRANSIT,
    DELIVERED
}

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val size: ProductSize,
    val price: Float,
    val imageUrl: String,
    val status: OrderStatus,
    val rating: Float? = null
)