package com.example.ecommerceapp.data.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.UIProductCardCartSize

object CartManager {
    var products by mutableStateOf(
        listOf(
            Product(
                id = 1,
                title = "Regular Fit Slogan",
                size = UIProductCardCartSize.LARGE,
                price = 1000F,
                imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112327/28212001066136.jpg?v=638745770039470000",
                quantity = 1
            ),
            Product(
                id = 2,
                title = "Basic Cotton T-shirt",
                size = UIProductCardCartSize.MEDIUM,
                price = 799.90F,
                imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112328/28212001066137.jpg?v=638745770039470000",
                quantity = 2
            ),
            Product(
                id = 3,
                title = "Slim Fit Jeans",
                size = UIProductCardCartSize.LARGE,
                price = 1899.99F,
                imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112340/28212001066140.jpg?v=638745770039470000",
                quantity = 1
            ),
            Product(
                id = 4,
                title = "Oversized Hoodie",
                size = UIProductCardCartSize.LARGE,
                price = 2499.50F,
                imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112342/28212001066142.jpg?v=638745770039470000",
                quantity = 1
            ),
            Product(
                id = 5,
                title = "Classic Polo Shirt",
                size = UIProductCardCartSize.MEDIUM,
                price = 1299.00F,
                imageUrl = "https://torratorra.vteximg.com.br/arquivos/ids/2112343/28212001066143.jpg?v=638745770039470000",
                quantity = 3
            )
        )
    )
        private set

    const val VAT_PERCENTAGE = 0.12F // 12%
    const val SHIPPING_FEE_MOCK = 14.99F

    val subtotal: Float
        get() = products.sumOf { (it.price * it.quantity).toDouble() }.toFloat()

    val vat: Float
        get() = subtotal * VAT_PERCENTAGE

    val shippingFee: Float
        get() = if (products.isEmpty()) 0F else SHIPPING_FEE_MOCK

    val total: Float
        get() = subtotal + vat + shippingFee

    fun incrementQuantity(productId: Int) {
        products = products.map {
            if (it.id == productId) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decrementQuantity(productId: Int) {
        products = products.map {
            if (it.id == productId && it.quantity > 1) it.copy(quantity = it.quantity - 1) else it
        }
    }

    fun removeProduct(productId: Int) {
        products = products.filterNot { it.id == productId }
    }

    fun clearCart() {
        products = emptyList()
    }
}
