package com.example.domain

import android.util.Log
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import javax.inject.Inject

class SaveFavUseCase @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(
        authId: String,
        asset_id: String,
        id_icon: String,
        name: String,
        price_usd: String
    ) {
        try {
            firestoreRepository.saveFav(authId, asset_id, id_icon, name, price_usd)
        } catch (e: Exception) {
            Log.e(e.message, e.message.toString())
        }
    }
}