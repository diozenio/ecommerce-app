package com.example.ecommerceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.components.NavItem
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UINavItem
import com.example.ecommerceapp.ui.components.UINavItemColors
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun AccountScreen() {
    val headingNavItems = listOf(
        NavItem("My Orders", UIIconName.Box, { /*...*/ }),
    )
    val mainNavItems = listOf(
        NavItem("My Details", UIIconName.Details, { /*...*/ }),
        NavItem("Address Book", UIIconName.Address, { /*...*/ }),
        NavItem("Payment Methods", UIIconName.Card, { /*...*/ }),
        NavItem("Notifications", UIIconName.Bell, { /*...*/ })
    )
    val bottomNavItems = listOf(
        NavItem("FAQs", UIIconName.Question, { /*...*/ }),
        NavItem("Help Center", UIIconName.Headphones, { /*...*/ }),
    )

    Column(Modifier.fillMaxSize()) {
        UINavHeader(title = "Account", onBackPressed = {}, onNotificationPressed = {})
        HorizontalDivider(Modifier.padding(horizontal = 32.dp), 1.dp, Colors.Primary100)

        headingNavItems.forEach {
            UINavItem(title = it.title, leftIcon = it.icon, onClick = it.onClick)
        }

        HorizontalDivider(thickness = 8.dp, color = Colors.Primary100)

        mainNavItems.forEach { item ->
            UINavItem(title = item.title, leftIcon = item.icon, onClick = item.onClick)
            HorizontalDivider(Modifier.padding(horizontal = 32.dp), 1.dp, Colors.Primary100)
        }

        HorizontalDivider(thickness = 8.dp, color = Colors.Primary100)

        bottomNavItems.forEach { item ->
            UINavItem(title = item.title, leftIcon = item.icon, onClick = item.onClick)
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
            onClick = { /*...*/ },
            colors = UINavItemColors(
                contentColor = Colors.Red,
            )
        )
    }
}

