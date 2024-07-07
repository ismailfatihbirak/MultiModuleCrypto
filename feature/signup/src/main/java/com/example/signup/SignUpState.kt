package com.example.signup

data class SignUpState(
    val isLoading: Boolean = false,
    val auth: Boolean = false,
    val error: String = "",
)