package com.example.ecommerceapp.data.order

import com.example.ecommerceapp.data.review.ReviewManager
import com.example.ecommerceapp.model.Order

class OrderManager(
    private val orderApi: OrderApi,
    private val reviewManager: ReviewManager
) {
    
    suspend fun loadOrders(): Result<List<Order>> {
        return try {
            val orders = orderApi.findAll()
            
            val orderIds: List<Int> = orders.map { it.id }
            val reviews = reviewManager.getReviewsForOrders(orderIds)
            
            val reviewsMap: Map<Int, com.example.ecommerceapp.model.OrderReview> =
                reviews.associateBy { it.orderId }
            
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

