package com.example.signup

internal sealed class SignUpUiEvent {
    data class NavigateToLoginScreen(val toastMessage: String? = null) : SignUpUiEvent()
}
