package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.saved.SavedApi
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedItem

class SavedRepository(
    private val savedApi: SavedApi
) {

    suspend fun getSavedItems(): List<SavedItem> {
        return try {
            val result = savedApi.getSavedItems()
            result
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun saveItem(product: Product) {
        try {
            savedApi.saveItem(product)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun unsaveItem(product: Product) {
        try {
            savedApi.unsaveItem(product.id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}