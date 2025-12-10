package com.example.ecommerceapp.data.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.model.OrderReview

@Dao
interface ReviewDao {
    @Insert
    suspend fun insertOne(item: OrderReview)

    @Update
    suspend fun updateOne(item: OrderReview)
    
    @Query("SELECT * FROM order_reviews WHERE orderId = :orderId")
    suspend fun findByOrderId(orderId: Int): OrderReview?
    
    @Query("SELECT * FROM order_reviews WHERE orderId IN (:orderIds)")
    suspend fun findByOrderIds(orderIds: List<Int>): List<OrderReview>
}

