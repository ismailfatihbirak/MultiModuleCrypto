package com.example.network.repository

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import com.example.network.retrofit.CryptoApi
import javax.inject.Inject

class CryptoDataSourceImpl @Inject constructor(private val api : CryptoApi)  : CryptoDataSource {
    override suspend fun getCrypto(): List<Root> {
        return api.getCrypto()
    }

    override suspend fun getAssetIdCrypto(assetId: String): RootId {
        return api.getAssetIdCrypto(assetId)
    }
}