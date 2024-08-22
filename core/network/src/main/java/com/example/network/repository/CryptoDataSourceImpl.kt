package com.example.network.repository

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import com.example.network.interceptor.RateLimitInterceptor
import com.example.network.retrofit.CryptoApi
import javax.inject.Inject

class CryptoDataSourceImpl @Inject constructor(
    private val api: CryptoApi,
    private val rateLimitInterceptor: RateLimitInterceptor
) : CryptoDataSource {
    override suspend fun getCrypto(): List<Root> {
        return api.getCrypto()
    }

    override suspend fun getAssetIdCrypto(assetId: String): RootId {
        return api.getAssetIdCrypto(assetId)
    }

    override suspend fun triggerInterceptor(): Boolean {
        return rateLimitInterceptor.triggeredRateLimit()
    }
}