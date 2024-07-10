package com.example.domain

import android.util.Log
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.RootId
import com.example.network.repository.CryptoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAssetIdCryptoUseCase @Inject constructor(private val repository: CryptoRepo) {
    operator fun invoke(assetId: String): Flow<Resource<RootId>> = flow {
        try {
            emit(Resource.Loading())
            val cryptoList = repository.getAssetIdCrypto(assetId)
            emit(Resource.Success(cryptoList))
        } catch (e: Exception) {
            Log.e("axax2",e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }
}





