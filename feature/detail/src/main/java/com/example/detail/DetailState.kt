package com.example.detail

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId

data class DetailState(
    val isLoading : Boolean = false,
    val cryptos : RootId = RootId(),
    val error : String = "",
)
