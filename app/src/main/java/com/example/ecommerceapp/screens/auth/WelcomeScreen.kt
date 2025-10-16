package com.example.ecommerceapp.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun WelcomeScreen(
    onNavigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UIText(
            text = "Welcome to Ecommerce App",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        UIText(
            text = "Your one-stop shop for everything you need",
            variant = UITextVariant.B1,
            color = Colors.Primary600
        )

        Spacer(modifier = Modifier.height(48.dp))

        UIButton(
            text = "Get Started",
            onClick = onNavigateToSignUp,
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
