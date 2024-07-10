package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {
    operator fun invoke(symbol: String): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val cryptoBoolean = firestoreRepository.deleteFav(symbol)
            emit(Resource.Success(cryptoBoolean))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}