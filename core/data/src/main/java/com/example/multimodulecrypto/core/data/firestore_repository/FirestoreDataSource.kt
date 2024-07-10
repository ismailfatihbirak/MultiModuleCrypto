package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.model.Root


interface FirestoreDataSource {
    suspend fun saveFav(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    )

    suspend fun getFavList(): List<Root>

    suspend fun deleteFav(symbol: String): Boolean
}