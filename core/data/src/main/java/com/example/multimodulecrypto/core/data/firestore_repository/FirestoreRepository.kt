package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.model.Root
import javax.inject.Inject

class FirestoreRepository @Inject constructor(private val firestoreDataSource: FirestoreDataSource) {
    suspend fun saveFav(
        authId: String,
        asset_id: String,
        id_icon: String,
        name: String,
        price_usd: String?
    ) {
        firestoreDataSource.saveFav(authId, asset_id, id_icon, name, price_usd)
    }

    suspend fun getFavList(authId: String): List<Root> {
        return firestoreDataSource.getFavList(authId)
    }

    suspend fun deleteFav(authId: String, asset_id: String): Boolean {
        return firestoreDataSource.deleteFav(authId, asset_id)
    }

}