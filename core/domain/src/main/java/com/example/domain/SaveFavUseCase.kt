package com.example.domain

import android.util.Log
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import javax.inject.Inject

class SaveFavUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ) {
        try {
            firestoreRepository.saveFav(
                id,
                symbol,
                name,
                image,
                currentPrice,
                priceChangePercentage
            )
        } catch (e: Exception) {
            Log.e(e.message, e.message.toString())
        }
    }
}