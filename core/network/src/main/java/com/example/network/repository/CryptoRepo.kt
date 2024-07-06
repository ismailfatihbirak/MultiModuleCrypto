package com.example.network.repository

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import okhttp3.OkHttpClient
import javax.inject.Inject

class CryptoRepo @Inject constructor(private val cryptoDataSource: CryptoDataSource) {
    suspend fun getCrypto(): List<Root> {
        return cryptoDataSource.getCrypto()
    }

    suspend fun getAssetIdCrypto(assetId: String): List<RootId> {
        return cryptoDataSource.getAssetIdCrypto(assetId)
    }

}