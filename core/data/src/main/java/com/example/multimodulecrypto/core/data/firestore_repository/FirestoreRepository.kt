package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.model.Root
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun saveFav(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ) {
        firestoreDataSource.saveFav(id, symbol, name, image, currentPrice, priceChangePercentage)
    }

    suspend fun getFavList(): List<Root> {
        return firestoreDataSource.getFavList()
    }

    suspend fun deleteFav(symbol: String): Boolean {
        return firestoreDataSource.deleteFav(symbol)
    }

}