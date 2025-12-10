package com.example.ecommerceapp.data.order

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ecommerceapp.data.core.BaseDao
import com.example.ecommerceapp.model.Order

@Dao
interface OrderDao : BaseDao<Order> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertOne(item: Order)

    @Update
    override suspend fun updateOne(item: Order)

    @Delete
    override suspend fun deleteOne(item: Order)

    @Query("DELETE FROM orders")
    override suspend fun deleteAll()

    @Query("SELECT * FROM orders")
    override suspend fun findAll(): List<Order>

    @Query("SELECT * FROM orders WHERE id = :id")
    override suspend fun findById(id: Int): Order?
}

