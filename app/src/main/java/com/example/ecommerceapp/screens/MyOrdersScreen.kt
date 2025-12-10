package com.example.ecommerceapp.screens

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectAsState
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.AppContainer
import com.example.ecommerceapp.model.Order
import com.example.ecommerceapp.model.OrderStatus
import com.example.ecommerceapp.screens.orders.MyOrdersViewModel
import com.example.ecommerceapp.screens.orders.MyOrdersViewModelFactory
import com.example.ecommerceapp.screens.orders.OrdersUiState
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIOrderCard
import com.example.ecommerceapp.ui.components.UIToggleControl
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UILeaveReviewModal
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIButtonVariant
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.theme.Colors

enum class OrderTab {
    ONGOING,
    COMPLETED
}

@Composable
fun MyOrdersScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val factory = MyOrdersViewModelFactory(
        orderManager = AppContainer.getOrderManager(context),
        reviewManager = AppContainer.getReviewManager(context)
    )
    val viewModel: MyOrdersViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf(OrderTab.ONGOING) }
    var orderToReview by remember { mutableStateOf<Order?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    val filteredOrders = when (val state = uiState) {
        is OrdersUiState.Success -> when (selectedTab) {
            OrderTab.ONGOING -> state.orders.filter {
                it.status in listOf(OrderStatus.PACKING, OrderStatus.PICKED, OrderStatus.IN_TRANSIT)
            }

            OrderTab.COMPLETED -> state.orders.filter { it.status == OrderStatus.DELIVERED }
        }

        else -> emptyList()
    }

    Scaffold(
        containerColor = Colors.Primary0
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = Colors.Primary0
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                UINavHeader(
                    title = "My Orders",
                    onBackPressed = { navController.popBackStack() },
                    onNotificationPressed = {
                        navController.navigate("notification")
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    thickness = 1.dp,
                    color = Colors.Primary100
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(vertical = 16.dp, horizontal = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UIToggleControl(
                        modifier = Modifier.fillMaxWidth(),
                        items = listOf("Ongoing", "Completed"),
                        selectedIndex = if (selectedTab == OrderTab.ONGOING) 0 else 1,
                        onItemSelected = { index ->
                            selectedTab = if (index == 0) OrderTab.ONGOING else OrderTab.COMPLETED
                        }
                    )
                }

                when (uiState) {
                    is OrdersUiState.Loading -> LoadingState(
                        modifier = Modifier.padding(6.dp)
                    )

                    is OrdersUiState.Error -> ErrorState(
                        message = (uiState as OrdersUiState.Error).message,
                        onRetry = { viewModel.loadOrders() },
                        modifier = Modifier.padding(6.dp)
                    )

                    is OrdersUiState.Success -> {
                        if (filteredOrders.isEmpty()) {
                            EmptyOrdersState(selectedTab)
                        } else {
                            OrdersList(
                                orders = filteredOrders,
                                onReviewClick = { orderToReview = it },
                                navController = navController,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }

                orderToReview?.let { order ->
                    UILeaveReviewModal(
                        orderTitle = order.title,
                        onDismiss = { orderToReview = null },
                        onSubmit = { rating, reviewText ->
                            viewModel.submitReview(order, rating, reviewText)
                            orderToReview = null
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = Colors.Primary900
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UIIcon(
            icon = UIIconName.WarningCircle,
            size = 64.dp,
            color = Colors.Primary300
        )
        Spacer(modifier = Modifier.height(20.dp))
        UIText(
            text = message,
            variant = UITextVariant.H4,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900
        )
        Spacer(modifier = Modifier.height(12.dp))
        UIText(
            text = "Verifique sua conexão com a internet e tente novamente.",
            variant = UITextVariant.B1,
            weight = UITextWeight.Regular,
            color = Colors.Primary500
        )
        Spacer(modifier = Modifier.height(32.dp))
        UIButton(
            text = "Tentar Novamente",
            onClick = onRetry,
            variant = UIButtonVariant.Primary
        )
    }
}

@Composable
private fun EmptyOrdersState(selectedTab: OrderTab) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (selectedTab) {
            OrderTab.ONGOING -> UIEmptyState(
                icon = UIIconName.BoxDuotone,
                title = "No Ongoing Orders!",
                description = "You don't have any ongoing orders at this time."
            )
            OrderTab.COMPLETED -> UIEmptyState(
                icon = UIIconName.BoxDuotone,
                title = "No Completed Orders!",
                description = "You don't have any completed orders at this time."
            )
        }
    }
}

@Composable
private fun OrdersList(
    orders: List<Order>,
    onReviewClick: (Order) -> Unit,
    navController: NavHostController?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(orders, key = { it.id }) { order ->
            UIOrderCard(
                order = order,
                onTrackOrderClick = {
                    navController?.navigate("order_delivery_screen/${order.id}")
                },
                onLeaveReviewClick = { onReviewClick(order) }
            )
        }
    }
}

