package com.example.ecommerceapp.data.review

import com.example.ecommerceapp.model.OrderReview

class ReviewManager(private val reviewDao: ReviewDao) {
    
    suspend fun getReviewsForOrders(orderIds: List<String>): List<OrderReview> {
        if (orderIds.isEmpty()) return emptyList()
        return reviewDao.findByOrderIds(orderIds)
    }
    
    suspend fun saveReview(orderId: String, rating: Float, reviewText: String) {
        val existing = reviewDao.findByOrderId(orderId)
        if (existing != null) {
            val updatedReview = existing.copy(
                rating = rating,
                reviewText = reviewText,
                timestamp = System.currentTimeMillis()
            )
            reviewDao.updateOne(updatedReview)
        } else {
            val review = OrderReview(
                orderId = orderId,
                rating = rating,
                reviewText = reviewText
            )
            reviewDao.insertOne(review)
        }
    }
}

