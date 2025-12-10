package com.example.ecommerceapp.model

data class OrderDelivery(
    val orderId: String,
    val currentLocation: LocationCoordinate,
    val destination: LocationCoordinate,
    val deliveryPerson: DeliveryPerson?,
    val statusHistory: List<OrderDeliveryStatus>
)

data class LocationCoordinate(
    val latitude: Double,
    val longitude: Double,
    val address: String? = null
)

data class DeliveryPerson(
    val name: String,
    val phone: String,
    val photo: String? = null
)

data class OrderDeliveryStatus(
    val status: OrderStatus,
    val location: String,
    val timestamp: Long,
    val isCompleted: Boolean
)

