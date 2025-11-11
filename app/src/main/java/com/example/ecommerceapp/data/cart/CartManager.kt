package com.example.ecommerceapp.data.cart

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.data.core.BaseManager
import com.example.ecommerceapp.data.core.RetrofitClient
import com.example.ecommerceapp.model.CartItem
import com.example.ecommerceapp.model.CartTaxes
import com.example.ecommerceapp.util.toCurrencyString

class CartManager(
    dao: CartDao,
) : BaseManager<CartItem>(dao) {
    var taxes: CartTaxes? = null
    var isLoadingTaxes by mutableStateOf(false)

    val subtotal: Float
        get() = items.sumOf { (it.product.price * it.quantity).toDouble() }.toFloat()

    val vat: String
        get() {
            if (isLoadingTaxes) return "Calculating..."
            if (taxes == null) return "Vat error"
            val vat = subtotal * (taxes?.vat ?: 0F)
            return vat.toCurrencyString()
        }

    val shippingFee: String
        get() {
            if (isLoadingTaxes) return "Calculating..."
            if (taxes == null) return "Shipping error"
            return if (items.isEmpty()) 0F.toCurrencyString() else (taxes?.shippingFee
                ?: 0F).toCurrencyString()
        }

    val total: Float
        get() = subtotal + (subtotal * (taxes?.vat ?: 0F)) + (taxes?.shippingFee ?: 0F)

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

    suspend fun findAllTaxes() {
        isLoadingTaxes = true
        taxes = try {
            RetrofitClient.cartApi.findAllTaxes()
        } catch (e: Exception) {
            Log.e("CartManager", "Error fetching taxes", e)
            null
        } finally {
            isLoadingTaxes = false
        }
    }

}
