package com.example.ecommerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecommerceapp.auth.AuthManager
import com.example.ecommerceapp.model.BottomNavBarItem
import com.example.ecommerceapp.screens.auth.LoginScreen
import com.example.ecommerceapp.screens.auth.SignUpScreen
import com.example.ecommerceapp.screens.auth.WelcomeScreen
import com.example.ecommerceapp.ui.components.UIBottomNavBar
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme

class MainActivity : ComponentActivity() {

    val bottomNavBarItems = listOf(
        BottomNavBarItem.HomeNavBarItem,
        BottomNavBarItem.SearchNavBarItem,
        BottomNavBarItem.SavedNavBarItem,
        BottomNavBarItem.CartNavBarItem,
        BottomNavBarItem.AccountNavBarItem,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcommerceAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    
                    if (AuthManager.isAuthenticated) {
                        MainApp()
                    } else {
                        AuthNavigation(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun AuthNavigation(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = "welcome"
        ) {
            composable("welcome") {
                WelcomeScreen(
                    onNavigateToSignUp = {
                        navController.navigate("signup")
                    }
                )
            }
            composable("signup") {
                SignUpScreen(
                    onSubmit = {
                        AuthManager.login()
                    },
                    onNavigateToLogin = {
                        navController.navigate("login")
                    }
                )
            }
            composable("login") {
                LoginScreen(
                    onSubmit = {
                        AuthManager.login()
                    },
                    onNavigateToSignUp = {
                        navController.navigate("signup")
                    }
                )
            }
        }
    }

    @Composable
    fun MainApp() {
        var selectedItem by remember {
            val item = bottomNavBarItems.first()
            mutableStateOf(item)
        }

        val pageState = rememberPagerState {
            bottomNavBarItems.size
        }

        LaunchedEffect(selectedItem) {
            val currentIndex = bottomNavBarItems.indexOf(selectedItem)
            pageState.animateScrollToPage(currentIndex)
        }

        LaunchedEffect(pageState.targetPage) {
            selectedItem = bottomNavBarItems[pageState.targetPage]
        }

        EcommerceApp(
            selectedItem = selectedItem,
            onBottomNavBarItemChange = { item ->
                selectedItem = item
            }
        ) {
            HorizontalPager(pageState) { page ->
                val item = bottomNavBarItems[page]
                item.screen()
            }
        }
    }

    @Composable
    fun EcommerceApp(
        selectedItem: BottomNavBarItem,
        onBottomNavBarItemChange: (BottomNavBarItem) -> Unit,
        content: @Composable () -> Unit
    ) {
        Scaffold(
            containerColor = Colors.Primary0,
            bottomBar = {
                UIBottomNavBar(
                    bottomNavBarItems,
                    selectedItem,
                    onItemChanged = onBottomNavBarItemChange
                )
            }
        ) { innerPadding ->
            Surface(
                color = Colors.Primary0,
                modifier = Modifier.padding(innerPadding)
            ) {
                content()
            }
        }
    }
}

