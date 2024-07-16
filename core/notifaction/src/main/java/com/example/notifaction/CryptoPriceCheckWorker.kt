package com.example.notifaction

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.work.HiltWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
import java.util.concurrent.TimeUnit

@HiltWorker
class CryptoPriceCheckWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    val getFavUseCase: GetFavUseCase,
    val getAssetIdCryptoUseCase: GetAssetIdCryptoUseCase
) : Worker(appContext, params) {
    override fun doWork(): Result {
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
                        checkCryptoPrice(crypto.id!!, notificationHandler)
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

    fun checkCryptoPrice(rootId: String, notificationHandler: NotificationHandler) {
        val assetId = rootId ?: return
        getAssetIdCryptoUseCase(assetId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    handleCryptoPrice(resource.data, root, notificationHandler)
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun handleCryptoPrice(cryptoData: RootId?, root: RootId, notificationHandler: NotificationHandler) {
        cryptoData ?: return
        val priceUsd = cryptoData.marketData!!.currentPrice!!.usd ?: return
        val priceChangePercent = (priceUsd - root.marketData!!.currentPrice!!.usd!!) / root.marketData!!.currentPrice!!.usd!! * 100
        if (priceChangePercent > 0.0001 || priceChangePercent < -0.0001) {
            notificationHandler.showSimpleNotification(
                cryptoData.name ?: "",
                priceChangePercent.toString()
            )
        }
    }


}