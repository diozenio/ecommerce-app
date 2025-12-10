package com.example.ecommerceapp.data.core

import com.example.ecommerceapp.data.auth.RemoteAuthService
import com.example.ecommerceapp.data.cart.RemoteCartApiDataSource
import com.example.ecommerceapp.data.notification.RemoteNotificationApiDataSource
import com.example.ecommerceapp.data.order.OrderApi
import com.example.ecommerceapp.data.product.ProductApi
import com.example.ecommerceapp.data.product.category.CategoryApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIService {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .build()

    private val client: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val remoteCartApiDataSource: RemoteCartApiDataSource =
        client.create(RemoteCartApiDataSource::class.java)
    val productApi: ProductApi = client.create(ProductApi::class.java)
    val categoryApi: CategoryApi = client.create(CategoryApi::class.java)
    val orderApi: OrderApi = client.create(OrderApi::class.java)
    val remoteAuthApi: RemoteAuthService = client.create(RemoteAuthService::class.java)

    val notificationApi: RemoteNotificationApiDataSource = client.create(
        RemoteNotificationApiDataSource::class.java
    )
}