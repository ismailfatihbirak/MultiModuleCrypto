package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.data.di.DataModule
import com.example.multimodulecrypto.core.model.Root
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreDataSourceImpl(
    @DataModule.IODispatcher private val ioDispatcher: CoroutineDispatcher
) : FirestoreDataSource {

    override suspend fun saveFav(
        authId: String,
        asset_id: String,
        id_icon: String,
        name: String,
        price_usd: String?
    ): Unit = withContext(ioDispatcher) {
        val price = price_usd?.toDoubleOrNull() ?: 0.0
        val fav = Root(asset_id, id_icon, name)
        //val fav = Root(asset_id, id_icon, name, price)
        Firebase.firestore.collection("Users").document(authId)
            .collection("FavoriteAssets").add(fav)
    }

    override suspend fun getFavList(authId: String): List<Root> = withContext(ioDispatcher) {
        suspendCoroutine { continuation ->
            val list = ArrayList<Root>()
            Firebase.firestore.collection("Users").document(authId)
                .collection("FavoriteAssets").get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val fav = document.toObject(Root::class.java)
                        fav?.let {
                            list.add(it)
                        }
                    }
                    continuation.resume(list)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun deleteFav(authId: String, asset_id: String): Boolean =
        withContext(ioDispatcher) {
            val query = Firebase.firestore.collection("Users").document(authId)
                .collection("FavoriteAssets").whereEqualTo("asset_id", asset_id)
            val result = query.get().await()
            for (document in result.documents) {
                document.reference.delete()
            }
            result.documents.isEmpty()
        }
}