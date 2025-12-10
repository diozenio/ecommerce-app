package com.example.ecommerceapp.data.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.ecommerceapp.data.core.BaseLocalDataSource
import com.example.ecommerceapp.model.CartItem

@Dao
interface LocalCartDataSource : BaseLocalDataSource<CartItem> {

    @Insert
    override suspend fun insertOne(item: CartItem)

    @Update
    override suspend fun updateOne(item: CartItem)

    @Delete
    override suspend fun deleteOne(item: CartItem)

    @Query("DELETE FROM cart")
    override suspend fun deleteAll()

    @Query("SELECT * FROM cart")
    override suspend fun findAll(): List<CartItem>

    @Query("SELECT * FROM cart WHERE id = :id")
    override suspend fun findById(id: Int): CartItem?

    @Upsert
    override suspend fun upsertAll(items: List<CartItem>)
}