package com.example.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val auth: Boolean = false,
    val error: String = "",
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val emailAuthControl: Boolean = false,
    val signUpCompleted: Boolean = false,
)