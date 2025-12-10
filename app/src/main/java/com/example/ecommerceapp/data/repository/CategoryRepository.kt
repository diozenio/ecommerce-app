package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.product.category.CategoryApi
import com.example.ecommerceapp.data.product.category.CategoryDao
import com.example.ecommerceapp.model.Category

class CategoryRepository(
    private val localDataSource: CategoryDao,
    private val remoteDataSource: CategoryApi
) {
    suspend fun getCategories(): List<Category> {
        return try {
            val remoteCategories = remoteDataSource.getCategories()
            localDataSource.insertAll(remoteCategories)
            remoteCategories
        } catch (e: Exception) {
            e.printStackTrace()
            localDataSource.findAll()
        }
    }
}