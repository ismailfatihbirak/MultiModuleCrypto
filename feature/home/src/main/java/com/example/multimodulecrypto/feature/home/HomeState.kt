package com.example.multimodulecrypto.feature.home

import com.example.multimodulecrypto.core.model.Root

data class HomeState(
    val isLoading : Boolean = false,
    val cryptos : List<Root> = listOf(),
    val error : String = "",
    val text : String = "",
    val searchList : List<Root> = listOf(),
    )
