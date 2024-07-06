package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.model.Root


interface FirestoreDataSource {
    suspend fun saveFav(
        authId: String,
        asset_id: String,
        id_icon: String,
        name: String,
        price_usd: String?
    )

    suspend fun getFavList(authId: String): List<Root>

    suspend fun deleteFav(authId: String, asset_id: String): Boolean
}