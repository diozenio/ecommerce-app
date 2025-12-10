package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.order.OrderApi
import com.example.ecommerceapp.model.Order

class OrderRepository(
    private val orderApi: OrderApi,
    private val reviewRepository: ReviewRepository
) {

    suspend fun loadOrders(): Result<List<Order>> {
        return try {
            val orders = orderApi.findAll()

            val orderIds: List<Int> = orders.map { it.id }
            val reviews = reviewRepository.getReviewsForOrders(orderIds)
            val reviewsMap = reviews.associateBy { it.orderId }

            val ordersWithReviews = orders.map { order ->
                val review = reviewsMap[order.id]
                if (review != null) {
                    order.copy(rating = review.rating)
                } else {
                    order
                }
            }

            Result.success(ordersWithReviews)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


