package com.example.ecommerceapp.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.auth.AuthManager
import com.example.ecommerceapp.util.validateEmail
import com.example.ecommerceapp.util.validateName
import com.example.ecommerceapp.util.validatePassword
import com.example.ecommerceapp.util.validatePasswordConfirmation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val passwordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,

    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val registrationSuccess: Boolean = false
)

class SignUpViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onNameChange(newName: String) {
        _uiState.update {
            it.copy(
                name = newName,
                nameError = if (it.nameError != null) validateName(newName) else null
            )
        }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                emailError = if (it.emailError != null) validateEmail(newEmail) else null
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(
                password = newPassword,
                passwordError = if (it.passwordError != null) validatePassword(newPassword) else null
            )
        }
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        _uiState.update {
            it.copy(
                confirmPassword = newConfirm,
                confirmPasswordError = if (it.confirmPasswordError != null)
                    validatePasswordConfirmation(it.password, newConfirm)
                else null
            )
        }
    }

    fun onTogglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun onToggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(confirmPasswordVisible = !it.confirmPasswordVisible) }
    }

    fun onSignUpClick() {
        val currentState = _uiState.value
        val nameError = validateName(currentState.name)
        val emailError = validateEmail(currentState.email)
        val passwordError = validatePassword(currentState.password)
        val confirmError = validatePasswordConfirmation(currentState.password, currentState.confirmPassword)

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmError
            )
        }

        if (nameError == null && emailError == null && passwordError == null && confirmError == null) {
            registerUser()
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val success = authManager.register(
                name = _uiState.value.name,
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            if (success) {
                _uiState.update {
                    it.copy(isLoading = false, registrationSuccess = true)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        emailError = "Este e-mail já está em uso"
                    )
                }
            }
        }
    }
}