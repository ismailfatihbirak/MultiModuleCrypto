package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import com.example.network.repository.CryptoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetAssetIdCryptoUseCase @Inject constructor(private val repository: CryptoRepo) {
    operator fun invoke(assetId: String): Flow<Resource<List<RootId>>> = flow {
        try {
            emit(Resource.Loading())
            val cryptoList = repository.getAssetIdCrypto(assetId)
            emit(Resource.Success(cryptoList))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}