package com.example.notifaction

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.domain.GetAssetIdCryptoUseCase
import com.example.domain.GetFavUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@HiltWorker
class CryptoPriceCheckWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    val getFavUseCase: GetFavUseCase,//Root
    val getAssetIdCryptoUseCase: GetAssetIdCryptoUseCase//RootId
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        priceCheck()
        return Result.success()
    }

    fun priceCheck() {
        val notificationHandler = NotificationHandler(applicationContext)
        val favState = mutableStateOf<FavState>(FavState())

        getFavUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cryptos = resource.data ?: emptyList()
                    favState.value = FavState(cryptos = cryptos)
                    cryptos.forEach { crypto ->
                        checkCryptoPrice(crypto, notificationHandler)
                    }
                }
                is Resource.Loading -> {
                    favState.value = FavState(isLoading = true)
                }
                is Resource.Error -> {
                    favState.value = FavState(error = resource.message ?: "Error")
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun checkCryptoPrice(crypto: Root, notificationHandler: NotificationHandler) {
        val assetId = crypto.id ?: return
        getAssetIdCryptoUseCase(assetId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    handleCryptoPrice(resource.data, crypto, notificationHandler)
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun handleCryptoPrice(cryptoDataCurrent: RootId?, rootSave: Root, notificationHandler: NotificationHandler) {
        cryptoDataCurrent ?: return
        val priceUsd = cryptoDataCurrent.marketData!!.currentPrice!!.usd ?: return
        val priceChangePercent = (priceUsd - rootSave.currentPrice!!) / rootSave.currentPrice!! * 100
        if (priceChangePercent > 0.0001 || priceChangePercent < -0.0001) {
            notificationHandler.showSimpleNotification(
                cryptoDataCurrent.name ?: "",
                priceChangePercent.toString()
            )
        }
    }


}