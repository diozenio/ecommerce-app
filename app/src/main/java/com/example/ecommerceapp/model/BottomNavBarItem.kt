package com.example.ecommerceapp.model

import androidx.compose.runtime.Composable
import com.example.ecommerceapp.screens.AccountScreen
import com.example.ecommerceapp.screens.CartScreen
import com.example.ecommerceapp.screens.HomeScreen
import com.example.ecommerceapp.screens.SavedScreen
import com.example.ecommerceapp.screens.SearchScreen
import com.example.ecommerceapp.ui.components.UIIconName

sealed class BottomNavBarItem(
    val label: String,
    val icon: UIIconName,
    val screen: @Composable () -> Unit,
) {
    object HomeNavBarItem : BottomNavBarItem(
        label = "Home",
        icon = UIIconName.Home,
        screen = { HomeScreen() },
    )

    object SearchNavBarItem : BottomNavBarItem(
        label = "Search",
        icon = UIIconName.Search,
        screen = { SearchScreen() },
    )

    object SavedNavBarItem : BottomNavBarItem(
        label = "Saved",
        icon = UIIconName.Heart,
        screen = { SavedScreen() },
    )

    object CartNavBarItem : BottomNavBarItem(
        label = "Cart",
        icon = UIIconName.Cart,
        screen = { CartScreen() },
    )

    object AccountNavBarItem : BottomNavBarItem(
        label = "Account",
        icon = UIIconName.User,
        screen = { AccountScreen() },
    )
}
