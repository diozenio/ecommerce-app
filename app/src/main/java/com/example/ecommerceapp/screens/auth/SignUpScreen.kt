package com.example.ecommerceapp.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun SignUpScreen(
    onSubmit: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UIText(
            text = "Create Account",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900
        )

        Spacer(modifier = Modifier.height(16.dp))

        UIText(
            text = "Join us and start shopping today",
            variant = UITextVariant.B1,
            color = Colors.Primary600
        )

        Spacer(modifier = Modifier.height(48.dp))

        UIButton(
            text = "Create an Account",
            onClick = onSubmit
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
            )
            Text(
                text = "Log In",
                modifier = Modifier.clickable { onNavigateToLogin() },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}
