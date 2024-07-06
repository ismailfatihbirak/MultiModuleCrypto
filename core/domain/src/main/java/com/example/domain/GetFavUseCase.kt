package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import com.example.multimodulecrypto.core.model.Root
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFavUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {
    operator fun invoke(authId: String): Flow<Resource<List<Root>>> = flow {
        try {
            emit(Resource.Loading())
            val cryptoList = firestoreRepository.getFavList(authId)
            emit(Resource.Success(cryptoList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}