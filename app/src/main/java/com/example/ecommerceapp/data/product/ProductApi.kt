package com.example.ecommerceapp.data.product

import com.example.ecommerceapp.model.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}