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
            
          val reviews = reviewManager.getReviewsForOrders(orderIds)
            
            val reviewsMap: Map<Int, com.example.ecommerceapp.model.OrderReview> =
                reviews.associateBy { it.orderId }
            
            
            Result.success(orders)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

