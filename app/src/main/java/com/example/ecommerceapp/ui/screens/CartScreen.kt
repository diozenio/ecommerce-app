package com.example.ecommerceapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIProductCardCart
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.state.CartScreenUIState
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.ui.viewModel.CartViewModel
import com.example.ecommerceapp.util.toCurrencyString

@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        state.fetchData()
    }

    if (state.isLoadingItems && state.cartItems.isEmpty()) {
        LoadingCart(navController)
    }

    if (state.cartItems.isEmpty()) {
        EmptyCart(navController)
    } else {
        FilledCart(state, navController)
    }
}

@Composable
fun LoadingCart(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        UINavHeader(
            title = "My Cart",
            onBackPressed = { navController.popBackStack() },
            onNotificationPressed = { navController.navigate("notification") }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp, start = 24.dp, end = 24.dp),
        ) {
            HorizontalDivider(thickness = 1.dp, color = Colors.Primary100)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Colors.Primary600)
            }
        }
    }
}

@Composable
fun EmptyCart(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        UINavHeader(
            title = "My Cart",
            onBackPressed = { navController.popBackStack() },
            onNotificationPressed = { navController.navigate("notification") }
        )
        UIEmptyState(
            icon = UIIconName.CartDuotone,
            title = "Your Cart Is Empty!",
            description = "When you add products, they'll appear here."
        )
    }
}


@Composable
fun FilledCart(state: CartScreenUIState, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        UINavHeader(
            title = "My Cart",
            onBackPressed = { navController.popBackStack() },
            onNotificationPressed = { navController.navigate("notification") }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(state.cartItems, key = { it.id }) { item ->
                UIProductCardCart(
                    title = item.product.title,
                    size = item.size,
                    price = item.product.price,
                    imageUrl = item.product.imageUrl,
                    quantity = item.quantity,
                    onRemoveItem = { state.onRemoveItem(item) },
                    onIncrement = { state.onIncrement(item.id) },
                    onDecrement = { state.onDecrement(item.id) },
                )
            }
            item {
                CartSummary(state)
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
                    modifier = Modifier.graphicsLayer(scaleX = -1f)
                )
            }
        )
    }
}

@Composable
fun CartSummary(state: CartScreenUIState) {
    Column {
        SummaryRow(label = "Sub-total", value = state.subtotal.toCurrencyString())
        SummaryRow(label = "VAT (%)", value = state.vat)
        SummaryRow(label = "Shipping fee", value = state.shippingFee)

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
            UIText(text = "Total", variant = UITextVariant.B1)
            UIText(
                text = state.total.toCurrencyString(),
                variant = UITextVariant.B1,
                weight = UITextWeight.SemiBold
            )
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UIText(text = label, variant = UITextVariant.B1, color = Colors.Primary500)
        UIText(text = value, variant = UITextVariant.B1, weight = UITextWeight.Medium)
    }
}