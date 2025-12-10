package com.example.ecommerceapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    val status: String,
    val total: Float,
    val createdAt: Long,
) : Model

enum class OrderStatus {
    PACKING,
    PICKED,
    IN_TRANSIT,
    DELIVERED
}

data class Order(
    val id: String,
    val title: String,
    val size: ProductSize,
    val price: Float,
    val imageUrl: String,
    val status: OrderStatus,
    val rating: Float? = null
)