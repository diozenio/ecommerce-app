package com.example.ecommerceapp.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecommerceapp.data.AppContainer
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UIInput
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun LoginScreen(
    onSubmit: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val factory = AppContainer.provideLoginViewModelFactory(LocalContext.current)
    val viewModel: LoginViewModel = viewModel(factory = factory)

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            onSubmit()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        UIText(
            text = "Bem-vindo de Volta",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900,
            modifier = Modifier.padding(top = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        UIText(
            text = "Entre na sua conta",
            variant = UITextVariant.B1,
            color = Colors.Primary600
        )

        Spacer(modifier = Modifier.height(48.dp))

        UIText(
            text = "E-mail",
            color = Colors.Primary900,
            weight = UITextWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        UIInput(
            text = uiState.email,
            onChangeValue = viewModel::onEmailChange,
            placeholderText = "Digite o seu email",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = uiState.loginError != null,
            supportingText = null
        )

        Spacer(modifier = Modifier.height(24.dp))

        UIText(
            text = "Senha",
            color = Colors.Primary900,
            weight = UITextWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        UIInput(
            text = uiState.password,
            onChangeValue = viewModel::onPasswordChange,
            placeholderText = "Digite a sua senha",
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (uiState.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = if (uiState.passwordVisible) UIIconName.EyeOff else UIIconName.Eye,
            onTrailingIconClick = viewModel::onTogglePasswordVisibility,
            isError = uiState.loginError != null,
            supportingText = uiState.loginError
        )

        Spacer(modifier = Modifier.height(24.dp))

        UIButton(
            text = "Entrar",
            onClick = viewModel::onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )

        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            UIText(
                text = "Não possui uma conta? ",
                variant = UITextVariant.B1,
                color = Colors.Primary600
            )
            UIText(
                text = "Cadastre-se",
                modifier = Modifier.clickable { onNavigateToSignUp() },
                variant = UITextVariant.B1,
                weight = UITextWeight.Medium,
                color = Colors.Primary900,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Preview(showBackground = true, name = "Tela de Login", device = "id:pixel_6")
@Composable
fun LoginScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Colors.Primary0) {
        LoginScreen(
            onSubmit = {},
            onNavigateToSignUp = {}
        )
    }
}