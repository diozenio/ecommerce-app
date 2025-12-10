package com.example.ecommerceapp.model

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
    val rating: Float? = null // Avaliação de 0.0 a 5.0, null se ainda não avaliado
)

