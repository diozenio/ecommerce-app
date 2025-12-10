package com.example.ecommerceapp.data.cart

import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.model.CartTaxes

class CartRepository(
    private val localDataSource: LocalCartDataSource,
    private val remoteDataSource: RemoteCartApiDataSource
) {
    suspend fun getCartItems(): List<CartItem> {
        return try {
            val remoteItems = remoteDataSource.findAll()
            localDataSource.upsertAll(remoteItems)
            remoteItems
        } catch (e: Exception) {
            localDataSource.findAll()
        }
    }

    suspend fun getTaxes(): CartTaxes? {
        return try {
            remoteDataSource.findAllTaxes()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateQuantity(item: CartItem, newQuantity: Int) {
        val updatedItem = item.copy(quantity = newQuantity)
        try {
            remoteDataSource.updateItem(item.id, updatedItem)
            localDataSource.updateOne(updatedItem)
        } catch (e: Exception) {
            localDataSource.updateOne(updatedItem)
        }
    }

    suspend fun removeItem(item: CartItem) {
        try {
            remoteDataSource.deleteItem(item.id)
            localDataSource.deleteOne(item)
        } catch (e: Exception) {
            localDataSource.deleteOne(item)
        }
    }
}