package com.example.ecommerceapp.data.cart

import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.model.CartTaxes
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteCartApiDataSource {
    @GET("cart")
    suspend fun findAll(@Query("userId") userId: String = "e8c57e6c-f3fc-458a-8384-39dc7e35aaa9"): List<CartItem>

    @GET("cart/taxes")
    suspend fun findAllTaxes(): CartTaxes

    @PUT("cart/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body item: CartItem): CartItem

    @DELETE("cart/{id}")
    suspend fun deleteItem(@Path("id") id: Int)
}