package com.example.ecommerceapp.data.cart

import com.example.ecommerceapp.data.core.BaseManager
import com.example.ecommerceapp.model.CartItem

class CartManager(
    dao: CartDao,
    private val vatPercentage: Float = 0.12F,
    private val shippingFeeMock: Float = 14.99F
) : BaseManager<CartItem>(dao) {

    val subtotal: Float
        get() = items.sumOf { (it.product.price * it.quantity).toDouble() }.toFloat()

    val vat: Float
        get() = subtotal * vatPercentage

    val shippingFee: Float
        get() = if (items.isEmpty()) 0F else shippingFeeMock

    val total: Float
        get() = subtotal + vat + shippingFee

    suspend fun incrementQuantity(itemId: Int) {
        val item = items.find { it.id == itemId }
        if (item == null) return

        val updatedItem = item.copy(quantity = item.quantity + 1)
        updateItem(updatedItem)
    }

    suspend fun decrementQuantity(itemId: Int) {
        val item = items.find { it.id == itemId }
        if (item == null) return

        val updatedItem = if (item.quantity > 1) {
            item.copy(quantity = item.quantity - 1)
        } else {
            return
        }

        updateItem(updatedItem)
    }
}
