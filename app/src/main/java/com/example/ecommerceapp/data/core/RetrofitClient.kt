package com.example.ecommerceapp.data.core

import com.example.ecommerceapp.data.cart.CartApi
import com.example.ecommerceapp.data.product.ProductApi
import com.example.ecommerceapp.data.product.category.CategoryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cartApi: CartApi = retrofit.create(CartApi::class.java)
    val productApi: ProductApi = retrofit.create(ProductApi::class.java)
    val categoryApi: CategoryApi = retrofit.create(CategoryApi::class.java)

}