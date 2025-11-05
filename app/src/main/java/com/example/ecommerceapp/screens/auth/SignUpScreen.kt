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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIInput
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun SignUpScreen(
    onSubmit: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToTerms: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        UIText(
            text = "Criar uma conta",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900
        )

        Spacer(modifier = Modifier.height(16.dp))

        UIText(
            text = "Vamos criar uma conta para você",
            variant = UITextVariant.B1,
            color = Colors.Primary600
        )

        Spacer(modifier = Modifier.height(48.dp))

        UIText(
            text = "Nome Completo",
            color = Colors.Primary900,
            weight = UITextWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        UIInput(
            text = nome,
            onChangeValue = { nome = it },
            placeholderText = "Digite o seu nome completo",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        UIText(
            text = "Email",
            color = Colors.Primary900,
            weight = UITextWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        UIInput(
            text = email,
            onChangeValue = { email = it },
            placeholderText = "Digite o seu email",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        UIText(
            text = "Senha",
            color = Colors.Primary900,
            weight = UITextWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        UIInput(
            text = senha,
            onChangeValue = { senha = it },
            placeholderText = "Digite a sua senha",
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(12.dp))
        val annotatedText = buildAnnotatedString {
            append("Ao clicar em Criar uma conta, você concorda com os nossos ")
            pushStringAnnotation(tag = "TERMS", annotation = "terms")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("termos de uso e política de privacidade")
            }
            pop()
        }

        Text(
            text = annotatedText,
            modifier = Modifier.clickable {
                onNavigateToTerms()
            }
        )


        Spacer(modifier = Modifier.height(24.dp))

        UIButton(
            text = "Criar uma conta",
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Já possui uma conta? ",
            )
            Text(
                text = "Entre",
                modifier = Modifier.clickable { onNavigateToLogin() },
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Preview(showBackground = true, name = "Sign Up Screen", device = "id:pixel_6")
@Composable
fun SignUpScreenPreview() {
    Surface(modifier = Modifier.fillMaxSize(), color = Colors.Primary0) {
        SignUpScreen(
            onSubmit = {},
            onNavigateToLogin = {},
            onNavigateToTerms = {}
        )
    }
}