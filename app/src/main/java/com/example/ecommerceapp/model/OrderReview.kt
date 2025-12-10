package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_reviews",
    indices = [Index(value = ["orderId"], unique = true)]
)
data class OrderReview(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val orderId: Int,
    val rating: Float,
    val reviewText: String,
    val timestamp: Long = System.currentTimeMillis()
) : Model

