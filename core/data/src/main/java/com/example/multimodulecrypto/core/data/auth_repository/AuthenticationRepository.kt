package com.example.multimodulecrypto.core.data.auth_repository

import android.content.Context
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val authenticationDataSource: AuthenticationDataSource) {
    suspend fun emailAuthenticationSignUp(
        email: String,
        password: String,
        context: Context
    ): Boolean {
        return authenticationDataSource.emailAuthenticationSignUp(email, password, context)
    }

    suspend fun emailAuthenticationSignIn(
        email: String,
        password: String,
        context: Context
    ): Boolean {
        return authenticationDataSource.emailAuthenticationSignIn(email, password, context)
    }
}