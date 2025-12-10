package com.example.ecommerceapp.screens

import android.accounts.AccountManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.MainViewModel
import com.example.ecommerceapp.data.AppContainer
import com.example.ecommerceapp.data.auth.AuthManager
import com.example.ecommerceapp.model.AccountNavItem
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UINavItem
import com.example.ecommerceapp.ui.components.UINavItemColors
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun AccountScreen(navController: NavHostController) {
    val topNavItems = listOf(
        AccountNavItem.MyOrdersItem,
    )
    val mainNavItems = listOf(
        AccountNavItem.MyDetailsItem, 
        AccountNavItem.AddressBookItem,
        AccountNavItem.PaymentMethodsItem,
        AccountNavItem.NotificationsItem,
    )
    val bottomNavItems = listOf(
        AccountNavItem.FAQsItem,
        AccountNavItem.HelpCenterItem,
    )

    Column(Modifier.fillMaxSize()) {
        UINavHeader(title = "Account", onBackPressed = {}, onNotificationPressed = {
            navController.navigate("notification")
        })
        topNavItems.forEach { item ->
            UINavItem(
                title = item.label,
                leftIcon = item.icon,
                onClick = { 
                    if (item.route.isNotEmpty()) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
        HorizontalDivider(thickness = 8.dp, color = Colors.Primary100)

        mainNavItems.forEach { item ->
            UINavItem(
                title = item.label,
                leftIcon = item.icon,
                onClick = { 
                    if (item.route.isNotEmpty()) {
                        navController.navigate(item.route)
                    }
                }
            )
            HorizontalDivider(Modifier.padding(horizontal = 32.dp), 1.dp, Colors.Primary100)
        }

        HorizontalDivider(thickness = 8.dp, color = Colors.Primary100)

        bottomNavItems.forEach { item ->
            UINavItem(title = item.label, leftIcon = item.icon, onClick = {
                navController.navigate(item.route)
            })
            HorizontalDivider(Modifier.padding(horizontal = 32.dp), 1.dp, Colors.Primary100)
        }

        HorizontalDivider(thickness = 8.dp, color = Colors.Primary100)

        UINavItem(
            title = "Log Out",
            leading = {
                UIIcon(
                    icon = UIIconName.Logout,
                    color = Colors.Red,
                    size = 24.dp,
                    modifier = Modifier.rotate(180f)
                )
            },
            onClick = { Log.d("AccountScreen", "Logout clicked") },
            colors = UINavItemColors(
                contentColor = Colors.Red,
            )
        )
    }
}
