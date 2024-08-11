package com.example.signup

internal sealed class SignUpUiEvent {
    object NavigateToLoginScreen : SignUpUiEvent()
    data class ShowToast(val message: String) : SignUpUiEvent()
}