package com.example.multimodulecrypto.feature.login

data class LoginState (
    val isLoading : Boolean = false,
    val auth : Boolean = false,
    val error : String = "",
    val email: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val emailAuthControl: Boolean = false,
    val signInCompleted: Boolean = false,
)