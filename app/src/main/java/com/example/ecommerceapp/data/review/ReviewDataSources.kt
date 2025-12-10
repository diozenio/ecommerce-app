package com.example.ecommerceapp.data.review

import com.example.ecommerceapp.model.OrderReview

interface LocalReviewDataSource {
    suspend fun findByOrderIds(orderIds: List<String>): List<OrderReview>
    suspend fun findByOrderId(orderId: String): OrderReview?
    suspend fun upsert(review: OrderReview)
}

class RoomLocalReviewDataSource(
    private val reviewDao: ReviewDao
) : LocalReviewDataSource {

    override suspend fun findByOrderIds(orderIds: List<String>): List<OrderReview> {
        return reviewDao.findByOrderIds(orderIds)
    }

    override suspend fun findByOrderId(orderId: String): OrderReview? {
        return reviewDao.findByOrderId(orderId)
    }

    override suspend fun upsert(review: OrderReview) {
        val existing = reviewDao.findByOrderId(review.orderId)
        if (existing != null) {
            val updated = existing.copy(
                rating = review.rating,
                reviewText = review.reviewText,
                timestamp = review.timestamp
            )
            reviewDao.updateOne(updated)
        } else {
            reviewDao.insertOne(review)
        }
    }
}


