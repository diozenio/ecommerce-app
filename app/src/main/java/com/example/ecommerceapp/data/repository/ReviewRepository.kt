package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.review.LocalReviewDataSource
import com.example.ecommerceapp.model.OrderReview

class ReviewRepository(
    private val localDataSource: LocalReviewDataSource
) {

    suspend fun getReviewsForOrders(orderIds: List<String>): List<OrderReview> {
        if (orderIds.isEmpty()) return emptyList()
        return localDataSource.findByOrderIds(orderIds)
    }

    suspend fun saveReview(orderId: String, rating: Float, reviewText: String) {
        val existing = localDataSource.findByOrderId(orderId)
        val review = if (existing != null) {
            existing.copy(
                rating = rating,
                reviewText = reviewText,
                timestamp = System.currentTimeMillis()
            )
        } else {
            OrderReview(
                orderId = orderId,
                rating = rating,
                reviewText = reviewText
            )
        }

        localDataSource.upsert(review)
    }
}


