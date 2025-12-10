package com.example.ecommerceapp.data.core

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<T>)

    @Delete
    suspend fun deleteOne(item: T)

    @Update
    suspend fun updateOne(item: T)
    suspend fun findAll(): List<T>

    suspend fun deleteAll()
}