package com.example.ecommerceapp.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.cart.CartRepository
import com.example.ecommerceapp.data.core.APIService
import com.example.ecommerceapp.data.core.DatabaseHelper
import com.example.ecommerceapp.screens.AccountScreen
import com.example.ecommerceapp.screens.SavedScreen
import com.example.ecommerceapp.screens.SearchScreen
import com.example.ecommerceapp.screens.home.HomeScreen
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.screens.CartScreen
import com.example.ecommerceapp.ui.viewModel.CartViewModel

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
        screen = { navController ->
            val context = LocalContext.current

            val viewModel: CartViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        val database = DatabaseHelper.getInstance(context)
                        val repository = CartRepository(
                            localDataSource = database.cartLocalDataSource(),
                            remoteDataSource = APIService.remoteCartApiDataSource
                        )
                        CartViewModel(repository)
                    }
                }
            )

            CartScreen(navController = navController, viewModel = viewModel)
        },
    )

    object AccountNavBarItem : BottomNavBarItem(
        label = "Account",
        icon = UIIconName.User,
        screen = { navController -> AccountScreen(navController) },
    )
}
