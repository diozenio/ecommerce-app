package com.example.ecommerceapp.data.core

import com.example.ecommerceapp.model.Model

interface BaseLocalDataSource<T : Model> {

    suspend fun findAll(): List<T>
    suspend fun findById(id: Int): T?
    suspend fun insertOne(item: T)
    suspend fun deleteOne(item: T)
    suspend fun updateOne(item: T)
    suspend fun deleteAll()
    suspend fun upsertAll(items: List<T>)
}