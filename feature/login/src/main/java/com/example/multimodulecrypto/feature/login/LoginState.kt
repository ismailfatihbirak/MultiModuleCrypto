package com.example.multimodulecrypto.feature.login

data class LoginState (
    val isLoading : Boolean = false,
    val auth : Boolean = false,
    val error : String = "",
)