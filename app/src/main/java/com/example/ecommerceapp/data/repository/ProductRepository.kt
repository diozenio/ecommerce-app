package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.product.ProductApi
import com.example.ecommerceapp.data.product.ProductDao
import com.example.ecommerceapp.model.Product

class ProductRepository(
    private val localDataSource: ProductDao,
    private val remoteDataSource: ProductApi
) {
    suspend fun getProducts(): List<Product> {
        return try {
            val remoteProducts = remoteDataSource.getProducts()
            localDataSource.insertAll(remoteProducts)
            remoteProducts
        } catch (e: Exception) {
            e.printStackTrace()
            localDataSource.findAll()
        }
    }
}