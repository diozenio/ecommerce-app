package com.example.ecommerceapp.data.order

import com.example.ecommerceapp.model.Order
import com.example.ecommerceapp.model.OrderDelivery
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderApi {
    @GET("orders")
    suspend fun findAll(): List<Order>
    
    @GET("orders/{id}/track")
    suspend fun getTracking(@Path("id") orderId: String): OrderDelivery
}

