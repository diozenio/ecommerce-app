package com.example.ecommerceapp.data.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.data.core.RetrofitClient
import com.example.ecommerceapp.model.Product

class ProductManager {
    var loadingState by mutableStateOf(false)
        private set
    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    suspend fun getProducts() {
        try {
            loadingState = true
            products = RetrofitClient.productApi.getProducts()
            Log.d("ProductManager", "Fetched ${products.size} products")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            loadingState = false
        }
    }

}