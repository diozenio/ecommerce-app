package com.example.ecommerceapp.data.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun findAll(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)
}