package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.saved.SavedApi
import com.example.ecommerceapp.data.saved.SavedDao
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedItem

class SavedRepository(
    private val savedApi: SavedApi,
    private val savedDao: SavedDao
) {

    suspend fun getSavedItems(): List<SavedItem> {
        return try {
            val remoteItems = savedApi.getSavedItems()
            savedDao.deleteAll()
            savedDao.insertAll(remoteItems)

            remoteItems
        } catch (e: Exception) {
            e.printStackTrace()
            savedDao.findAll()
        }
    }

    suspend fun saveItem(product: Product) {
        try {
            val savedItem = savedApi.saveItem(product)
            savedDao.insertOne(savedItem)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun unsaveItem(product: Product) {
        try {
            savedApi.unsaveItem(product.id)
            val localItems = savedDao.findAll()
            val itemToDelete = localItems.find { it.product.id == product.id }

            if (itemToDelete != null) {
                savedDao.deleteOne(itemToDelete)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}