package com.example.multimodulecrypto.core.data.firestore_repository

import com.example.multimodulecrypto.core.data.di.DataModule
import com.example.multimodulecrypto.core.model.Root
import com.google.firebase.auth.auth
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
    private val authId = com.google.firebase.Firebase.auth.currentUser!!.uid
    override suspend fun saveFav(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ): Unit = withContext(ioDispatcher) {
        val price = currentPrice?.toDoubleOrNull() ?: 0.0
        val fav = Root(
            id = id,
            symbol = symbol,
            name = name,
            image = image,
            currentPrice = price,
            priceChangePercentage24h = priceChangePercentage
        )
        Firebase.firestore.collection("Users").document(authId)
            .collection("FavoriteAssets").add(fav)
    }

    override suspend fun getFavList(): List<Root> = withContext(ioDispatcher) {
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

    override suspend fun deleteFav(symbol: String): Boolean =
        withContext(ioDispatcher) {
            val query = Firebase.firestore.collection("Users").document(authId)
                .collection("FavoriteAssets").whereEqualTo("symbol", symbol)
            val result = query.get().await()
            for (document in result.documents) {
                document.reference.delete()
            }
            result.documents.isEmpty()
        }
}