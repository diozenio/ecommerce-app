package com.example.ecommerceapp.data.saved

import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object ItemSaved {
    private val _savedItems = MutableStateFlow<List<SavedItem>>(emptyList())
    val savedItems = _savedItems.asStateFlow()

    fun addItem(product: Product) {
        _savedItems.update { currentList ->
            if (currentList.any { it.product.id == product.id }) {
                currentList
            } else {
                val newSavedItem = SavedItem(product = product)
                currentList + newSavedItem
            }
        }
    }

    fun removeItem(product: Product) {
        _savedItems.update { currentList ->
            currentList.filterNot { it.product.id == product.id }
        }
    }

    fun isItemSaved(productId: String): Boolean {
        return _savedItems.value.any { it.product.id == productId }
    }
}
