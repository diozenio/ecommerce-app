package com.example.ecommerceapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.saved.ItemSaved
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SavedViewModel : ViewModel() {
    val savedItems: StateFlow<List<Product>> = ItemSaved.savedItems
    val hasSavedItems: StateFlow<Boolean> = savedItems
        .map { it.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    init {
        ItemSaved.addMockData()
    }

    fun unsaveItem(product: Product) {
        ItemSaved.removeItem(product)
    }
}
