package com.example.network.retrofit

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CryptoApi {
    @GET("coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCrypto(): List<Root>

    @GET("coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=true")
    suspend fun getAssetIdCrypto(@Path("id") coinId: String): RootId

}