package com.example.ecommerceapp.data.product.category

import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import retrofit2.http.GET

interface CategoryApi {
    @GET("categories")
    suspend fun getCategories(): List<Category>
}