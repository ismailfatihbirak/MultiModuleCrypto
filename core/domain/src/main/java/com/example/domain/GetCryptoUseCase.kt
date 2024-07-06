package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
import com.example.network.repository.CryptoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(
    private val repository: CryptoRepo
) {
    operator fun invoke(): Flow<Resource<List<Root>>> = flow {
        try {
            emit(Resource.Loading())
            val cryptoList = repository.getCrypto()
            emit(Resource.Success(cryptoList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}