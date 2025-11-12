package com.example.ecommerceapp.model

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.ecommerceapp.screens.AccountScreen
import com.example.ecommerceapp.screens.CartScreen
import com.example.ecommerceapp.screens.HomeScreen
import com.example.ecommerceapp.screens.SavedScreen
import com.example.ecommerceapp.screens.SearchScreen
import com.example.ecommerceapp.ui.components.UIIconName

sealed class BottomNavBarItem(
    val label: String,
    val icon: UIIconName,
    val screen: @Composable (navController: NavHostController) -> Unit,
) {
    object HomeNavBarItem : BottomNavBarItem(
        label = "Home",
        icon = UIIconName.Home,
        screen = { navController -> HomeScreen(navController) },
    )

    object SearchNavBarItem : BottomNavBarItem(
        label = "Search",
        icon = UIIconName.Search,
        screen = { navController -> SearchScreen(navController) },
    )

    object SavedNavBarItem : BottomNavBarItem(
        label = "Saved",
        icon = UIIconName.Heart,
        screen = { navController -> SavedScreen(navController) },
    )

    object CartNavBarItem : BottomNavBarItem(
        label = "Cart",
        icon = UIIconName.Cart,
        screen = { navController -> CartScreen(navController) },
    )

    object AccountNavBarItem : BottomNavBarItem(
        label = "Account",
        icon = UIIconName.User,
        screen = { navController -> AccountScreen(navController) },
    )
}
