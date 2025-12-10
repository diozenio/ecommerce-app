package com.example.ecommerceapp.data.core

import com.example.ecommerceapp.data.auth.RemoteAuthService
import com.example.ecommerceapp.data.cart.CartApi
import com.example.ecommerceapp.data.notification.RemoteNotificationApiDataSource
import com.example.ecommerceapp.data.product.ProductApi
import com.example.ecommerceapp.data.product.category.CategoryApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    private val client: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cartApi: CartApi = client.create(CartApi::class.java)
    val productApi: ProductApi = client.create(ProductApi::class.java)
    val categoryApi: CategoryApi = client.create(CategoryApi::class.java)
    val remoteAuthApi: RemoteAuthService = client.create(RemoteAuthService::class.java)

    val notificationApi: RemoteNotificationApiDataSource = client.create(
        RemoteNotificationApiDataSource::class.java
    )
}