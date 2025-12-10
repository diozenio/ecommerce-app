package com.example.ecommerceapp.data.product.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.model.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun findAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<Category>)
}