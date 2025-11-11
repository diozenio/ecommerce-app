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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.data.cart.CartManager
import com.example.ecommerceapp.data.core.DatabaseHelper
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
import kotlinx.coroutines.launch

@Composable
fun CartScreen() {
    val context = LocalContext.current
    val dao = remember { DatabaseHelper.getInstance(context).cartDao() }
    val manager = remember { CartManager(dao) }

    LaunchedEffect(Unit) {
        manager.findAll()
        manager.findAllTaxes()
    }

    if (manager.items.isEmpty()) {
        EmptyCart()
    } else {
        FilledCart(manager)
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
fun FilledCart(manager: CartManager) {
    val items = manager.items
    val scope = rememberCoroutineScope()

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
            items(items, key = { it.id }) { item ->
                UIProductCardCart(
                    title = item.product.title,
                    size = item.size,
                    price = item.product.price,
                    imageUrl = item.product.imageUrl,
                    quantity = item.quantity,
                    onRemoveItem = {
                        scope.launch {
                            manager.removeItem(item)
                        }
                    },
                    onIncrement = {
                        scope.launch {
                            manager.incrementQuantity(item.id)
                        }
                    },
                    onDecrement = {
                        scope.launch {
                            manager.decrementQuantity(item.id)
                        }
                    },
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
                            text = manager.subtotal.toCurrencyString(),
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
                            text = manager.vat,
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
                            text = manager.shippingFee,
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
                            text = manager.total.toCurrencyString(),
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

