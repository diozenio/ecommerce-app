package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.order.LocalOrderDataSource
import com.example.ecommerceapp.data.order.RemoteOrderApiDataSource
import com.example.ecommerceapp.model.Order

class OrderRepository(
    private val localDataSource: LocalOrderDataSource,
    private val remoteDataSource: RemoteOrderApiDataSource
) {

    suspend fun findAll(): List<Order> {
        return try {
            val remoteOrders = remoteDataSource.findAll()

            localDataSource.upsertAll(remoteOrders)

            remoteOrders
        } catch (e: Exception) {
            localDataSource.findAll()
        }
    }
}

