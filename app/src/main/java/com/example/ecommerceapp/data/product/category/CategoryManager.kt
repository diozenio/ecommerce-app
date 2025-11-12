package com.example.ecommerceapp.data.product.category

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.data.core.RetrofitClient
import com.example.ecommerceapp.model.Category

class CategoryManager {
    var loadingState by mutableStateOf(false)
        private set

    var categories by mutableStateOf(
        listOf(
            Category("12343", "All")
        )
    )
        private set

    suspend fun getCategories() {
        try {
            loadingState = true
            categories = categories + RetrofitClient.categoryApi.getCategories()

            Log.d("CategoryManager", "Fetched ${categories.size} categories")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            loadingState = false
        }
    }
}