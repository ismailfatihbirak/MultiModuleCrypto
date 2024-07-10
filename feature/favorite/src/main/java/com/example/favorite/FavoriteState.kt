package com.example.favorite

import com.example.multimodulecrypto.core.model.Root

data class FavoriteState(
    val isLoading : Boolean = false,
    val cryptos : List<Root> = listOf(),
    val error : String = "",
    val delete : Boolean = false
)
