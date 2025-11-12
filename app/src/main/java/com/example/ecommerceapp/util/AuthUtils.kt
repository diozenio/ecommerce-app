package com.example.ecommerceapp.util

import android.util.Patterns

fun validateName(name: String): String? {
    if (name.trim().isEmpty()) {
        return "Por favor, preencha o seu nome."
    }
    return null
}

fun validateEmail(email: String): String? {
    if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Por favor, insira um e-mail válido."
    }
    return null
}

fun validatePassword(password: String): String? {
    if (password.length < 6) {
        return "A password deve ter pelo menos 6 caracteres."
    }
    return null
}

fun validatePasswordConfirmation(password: String, confirmPassword: String): String? {
    if (password != confirmPassword) {
        return "As senhas não coincidem."
    }
    return null
}