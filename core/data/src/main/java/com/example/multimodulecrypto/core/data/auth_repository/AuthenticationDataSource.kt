package com.example.multimodulecrypto.core.data.auth_repository

import android.content.Context

interface AuthenticationDataSource {
    suspend fun emailAuthenticationSignUp(
        email: String,
        password: String,
        context: Context
    ): Boolean

    suspend fun emailAuthenticationSignIn(
        email: String,
        password: String,
        context: Context
    ): Boolean
}