package com.example.ecommerceapp.data.cart

import com.example.ecommerceapp.model.CartTaxes
import retrofit2.http.GET

interface CartApi {
    @GET("taxes")
    suspend fun findAllTaxes(): CartTaxes
}