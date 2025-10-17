package com.example.ecommerceapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.data.cart.CartManager
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIProductCardCart
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.util.toCurrencyString

@Composable
fun CartScreen() {
    val products = CartManager.products

    if (products.isEmpty()) {
        EmptyCart()
    } else {
        FilledCart()
    }
}

@Composable
fun EmptyCart() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        UINavHeader(
            title = "My Cart",
            onBackPressed = { Log.d("UINavHeader", "Mensagem de onBackPressed") },
            onNotificationPressed = { Log.d("UINavHeader", "Mensagem de onNotificationPressed") }
        )

        UIEmptyState(
            icon = UIIconName.CartDuotone,
            title = "Your Cart Is Empty!",
            description = "When you add products, they'll appear here."
        )
    }
}

@Composable
fun FilledCart() {
    val products = CartManager.products

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        UINavHeader(
            title = "My Cart",
            onBackPressed = { Log.d("UINavHeader", "Mensagem de onBackPressed") },
            onNotificationPressed = { Log.d("UINavHeader", "Mensagem de onNotificationPressed") }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(products, key = { it.id }) { product ->
                UIProductCardCart(
                    title = product.title,
                    size = product.size,
                    price = product.price,
                    imageUrl = product.imageUrl,
                    quantity = product.quantity,
                    onRemoveItem = { CartManager.removeProduct(product.id) },
                    onIncrement = { CartManager.incrementQuantity(product.id) },
                    onDecrement = { CartManager.decrementQuantity(product.id) },
                )
            }
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        UIText(
                            text = "Sub-total",
                            variant = UITextVariant.B1,
                            color = Colors.Primary500
                        )
                        UIText(
                            text = CartManager.subtotal.toCurrencyString(),
                            variant = UITextVariant.B1,
                            weight = UITextWeight.Medium
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        UIText(
                            text = "VAT (%)",
                            variant = UITextVariant.B1,
                            color = Colors.Primary500
                        )
                        UIText(
                            text = CartManager.vat.toCurrencyString(),
                            variant = UITextVariant.B1,
                            weight = UITextWeight.Medium
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        UIText(
                            text = "Shipping fee",
                            variant = UITextVariant.B1,
                            color = Colors.Primary500
                        )
                        UIText(
                            text = CartManager.shippingFee.toCurrencyString(),
                            variant = UITextVariant.B1,
                            weight = UITextWeight.Medium
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = Colors.Primary100
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        UIText(
                            text = "Total",
                            variant = UITextVariant.B1,
                        )
                        UIText(
                            text = CartManager.total.toCurrencyString(),
                            variant = UITextVariant.B1,
                            weight = UITextWeight.SemiBold
                        )
                    }
                }
            }
        }

        UIButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 24.dp),
            text = "Go To Checkout",
            rightIcon = {
                UIIcon(
                    icon = UIIconName.Arrow,
                    color = Colors.Primary0,
                    modifier = Modifier.graphicsLayer(
                        scaleX = -1f
                    )
                )
            }
        )
    }
}

