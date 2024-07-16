package com.example.notifaction

import com.example.multimodulecrypto.core.model.Root

data class FavState(
    val isLoading: Boolean = false,
    val cryptos: List<Root> = listOf(),
    val error: String = "",
)
