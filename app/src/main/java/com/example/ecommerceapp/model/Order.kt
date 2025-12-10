package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ecommerceapp.model.Model

enum class OrderStatus {
    PACKING,
    PICKED,
    IN_TRANSIT,
    DELIVERED
}

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = false)
    override val id: Int = 0,
    val title: String,
    val size: ProductSize,
    val price: Float,
    val imageUrl: String,
    val status: OrderStatus,
    val rating: Float? = null
): Model