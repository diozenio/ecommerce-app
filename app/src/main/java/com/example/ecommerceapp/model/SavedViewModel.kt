package com.example.ecommerceapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.repository.SavedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class SavedViewModel(
    private val repository: SavedRepository
) : ViewModel() {

    private val _savedItems = MutableStateFlow<List<SavedItem>>(emptyList())
    val savedItems: StateFlow<List<SavedItem>> = _savedItems.asStateFlow()
    val hasSavedItems: StateFlow<Boolean> = _savedItems
        .map { it.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    init {
        fetchSavedItems()
    }

    fun fetchSavedItems() {
        viewModelScope.launch {
            val items = repository.getSavedItems()
            _savedItems.value = items
        }
    }

    fun unsaveItem(product: Product) {
        viewModelScope.launch {
            repository.unsaveItem(product)
            fetchSavedItems()
        }
    }

    fun saveItem(product: Product) {
        viewModelScope.launch {
            repository.saveItem(product)
            fetchSavedItems()
        }
    }
}