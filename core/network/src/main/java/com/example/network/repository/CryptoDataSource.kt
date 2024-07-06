package com.example.network.repository

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId

interface CryptoDataSource {
    suspend fun getCrypto(): List<Root>
    suspend fun getAssetIdCrypto(assetId: String): List<RootId>
}