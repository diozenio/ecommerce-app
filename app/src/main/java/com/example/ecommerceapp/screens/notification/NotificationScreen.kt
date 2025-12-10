package com.example.ecommerceapp.screens.notification

import NotificationViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.core.APIService
import com.example.ecommerceapp.data.core.DatabaseHelper
import com.example.ecommerceapp.data.notification.RoomLocalNotificationDataSource
import com.example.ecommerceapp.data.repository.NotificationRepository
import com.example.ecommerceapp.model.Notification
import com.example.ecommerceapp.model.NotificationCategory
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.util.shimmerEffect

@Composable
fun NotificationScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val database = remember { DatabaseHelper.getInstance(context) }
    val dao = database.notificationDao()
    val localDataSource =
        remember { RoomLocalNotificationDataSource(dao) }

    val remoteDataSource = remember { APIService.notificationApi }

    val repository = remember { NotificationRepository(localDataSource, remoteDataSource) }

    val viewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModel.Factory(repository)
    )

    val uiState by viewModel.uiState.collectAsState()
    val groupedNotifications = uiState.groupedNotifications
    var isLoading = uiState.isLoading


    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Column(
                modifier = modifier.fillMaxSize()
            ) {

                UINavHeader(
                    title = "Notification",
                    onBackPressed = { navController.popBackStack() },
                )

                Column(
                    modifier = modifier.padding(horizontal = 24.dp)
                ) {
                    when {
                        isLoading -> {
                            LazyColumn {
                                items(6) {
                                    NotificationSkeletonItem()
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }

                        groupedNotifications.isEmpty() -> {
                            UIEmptyState(
                                icon = UIIconName.BellDuotone,
                                title = "You haven’t gotten any notifications yet!",
                                description = "We’ll alert you when something cool happens."
                            )
                        }

                        else -> {
                            LazyColumn {
                                groupedNotifications.forEach { (dateGroup, notifications) ->
                                    item {
                                        UIText(
                                            text = dateGroup,
                                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                                            weight = UITextWeight.SemiBold
                                        )

                                        if (dateGroup != "Today") {
                                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.7f))
                                        }
                                    }

                                    items(notifications) { notification ->
                                        NotificationItem(notification = notification)

                                        if (notification != notifications.last()) {
                                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.7f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            if (notification.category == NotificationCategory.OFFER) {
                UIIcon(
                    UIIconName.DiscountDuotone
                )
            } else if (notification.category == NotificationCategory.PAYMENT) {
                UIIcon(
                    UIIconName.WalletDuotone
                )
            } else if (notification.category == NotificationCategory.PROFILE) {
                UIIcon(
                    UIIconName.UserDuotone
                )
            }


            Column {
                UIText(
                    text = notification.title,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }


    }
}

@Composable
fun NotificationSkeletonItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )

            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}