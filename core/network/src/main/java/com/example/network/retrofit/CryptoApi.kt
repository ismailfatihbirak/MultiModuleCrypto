package com.example.network.retrofit

import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//private const val API_KEY = "0E56E037-D8D9-4B1B-BF20-C7AAA1D15CB0"

interface CryptoApi {
//    @GET("assets")
//    suspend fun getCrypto(
//        @Query("apikey") apiKey :String = API_KEY
//    ):List<Root>
//
//    @GET("assets/{asset_id}")
//    suspend fun getAssetIdCrypto(
//        @Path("asset_id") asset_id : String,
//        @Query("apikey") apiKey :String = API_KEY
//    ):List<Root>

    // https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&sparkline=true
    @GET("coins/markets?vs_currency=usd&sparkline=true")
    suspend fun getCrypto(): List<Root>

    @GET("coins/{id}")
    fun getAssetIdCrypto(
        @Path("id") id: String,
        @Query("localization") localization: Boolean = false,
        @Query("tickers") tickers: Boolean = false,
        @Query("market_data") marketData: Boolean = true,
        @Query("community_data") communityData: Boolean = false,
        @Query("developer_data") developerData: Boolean = false,
        @Query("sparkline") sparkline: Boolean = false
    ): List<RootId>
}