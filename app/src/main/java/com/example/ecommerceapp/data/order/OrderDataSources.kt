package com.example.ecommerceapp.data.order

import com.example.ecommerceapp.model.Order
import retrofit2.http.GET

interface LocalOrderDataSource {
    suspend fun upsertAll(orders: List<Order>)
    suspend fun findAll(): List<Order>
    suspend fun deleteAll()
}

class RoomLocalOrderDataSource(
    private val dao: OrderDao
) : LocalOrderDataSource {

    override suspend fun upsertAll(orders: List<Order>) {
        orders.forEach { dao.insertOne(it) }
    }

    override suspend fun findAll(): List<Order> {
        return dao.findAll()
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}

interface RemoteOrderApiDataSource {
    @GET("orders")
    suspend fun findAll(): List<Order>
}

