package com.example.ecommerceapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.cart.CartRepository
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.ui.state.CartScreenUIState
import com.example.ecommerceapp.util.toCurrencyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenUIState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onRemoveItem = { item -> removeItem(item) },
                onIncrement = { id -> changeQuantity(id, 1) },
                onDecrement = { id -> changeQuantity(id, -1) },
                fetchData = { fetchData() }
            )
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoadingItems = true)
            }

            val items = repository.getCartItems()
            val taxes = repository.getTaxes()

            val subtotal = items.sumOf { (it.product.price * it.quantity).toDouble() }.toFloat()
            val vatRate = taxes?.vat ?: 0f
            val shipping = if (items.isEmpty()) 0f else (taxes?.shippingFee ?: 0f)

            val vatValue = subtotal * vatRate
            val total = subtotal + vatValue + shipping

            _uiState.update {
                it.copy(
                    cartItems = items,
                    subtotal = subtotal,
                    vat = vatValue.toCurrencyString(),
                    shippingFee = shipping.toCurrencyString(),
                    total = total
                )
            }
        }
    }

    private fun changeQuantity(itemId: Int, delta: Int) {
        val currentItems = _uiState.value.cartItems
        val item = currentItems.find { it.id == itemId } ?: return
        val newQuantity = item.quantity + delta

        if (newQuantity < 1) return

        viewModelScope.launch {
            repository.updateQuantity(item, newQuantity)
            fetchData()
        }
    }

    private fun removeItem(item: CartItem) {
        viewModelScope.launch {
            repository.removeItem(item)
            fetchData()
        }
    }
}