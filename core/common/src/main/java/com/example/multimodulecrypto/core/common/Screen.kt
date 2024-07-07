package com.example.multimodulecrypto.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object WelcomeScreen : Screen()

    @Serializable
    object SignUpScreen : Screen()

    @Serializable
    object LoginScreen : Screen()

    @Serializable
    object HomeScreen : Screen()

    @Serializable
    object FavoriteScreen : Screen()

    @Serializable
    object DetailScreen : Screen()
}