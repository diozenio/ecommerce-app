package com.example.ecommerceapp.ui.state

import com.example.ecommerceapp.model.CartItem

data class CartScreenUIState(
    val cartItems: List<CartItem> = emptyList(),
    val subtotal: Float = 0f,
    val vat: String = "",
    val shippingFee: String = "",
    val total: Float = 0f,
    val isLoadingItems: Boolean = false,

    val onRemoveItem: (CartItem) -> Unit = {},
    val onIncrement: (Int) -> Unit = {},
    val onDecrement: (Int) -> Unit = {},
    val fetchData: () -> Unit = {},
)