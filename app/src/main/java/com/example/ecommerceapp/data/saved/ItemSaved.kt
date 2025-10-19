package com.example.ecommerceapp.data.saved

import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.UIProductCardCartSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object ItemSaved {
    private val _savedItems = MutableStateFlow<List<Product>>(emptyList())
    val savedItems = _savedItems.asStateFlow()

    fun addItem(product: Product) {
        _savedItems.update { currentList ->
            if (currentList.any { it.id == product.id }) {
                currentList
            } else {
                currentList + product.copy(isSaved = true)
            }
        }
    }

    fun removeItem(product: Product) {
        _savedItems.update { currentList ->
            currentList.filterNot { it.id == product.id }
        }
    }

    fun isItemSaved(productId: Int): Boolean {
        return _savedItems.value.any { it.id == productId }
    }

    fun clearAll() {
        _savedItems.value = emptyList()
    }

    fun addMockData() {
        if (_savedItems.value.isEmpty()) {
            val mockItems = listOf(
                Product(id = 1, title = "white t-shirt", size = UIProductCardCartSize.MEDIUM, price = 79.90f, imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112328/28212001066137.jpg?v=638745770039470000", quantity = 1),
                Product(id = 2, title = "Regular Jeans", size = UIProductCardCartSize.LARGE, price = 199.99f, imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112327/28212001066136.jpg?v=638745770039470000", quantity = 1),
                Product(id = 3, title = "Polo Wear", size = UIProductCardCartSize.SMALL, price = 349.50f, imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112343/28212001066143.jpg?v=638745770039470000\"", quantity = 1)
            )
            _savedItems.value = mockItems.map { it.copy(isSaved = true) }
        }
    }
}
