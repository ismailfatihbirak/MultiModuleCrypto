package com.example.notifaction

import android.content.Context
import android.util.Log
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

        getFavUseCase().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cryptos = resource.data ?: emptyList()
                    cryptos.forEach { crypto ->
                        checkCryptoPrice(crypto, notificationHandler)
                    }
                }

                is Resource.Loading -> {
                    // Handle loading state if needed
                }

                is Resource.Error -> {
                    // Handle error state, you can log or show a toast message
                    val errorMessage = resource.message ?: "Error"
                    Log.e("PriceCheck", errorMessage)
                    // Show a toast message or notification if needed
                    // Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    fun checkCryptoPrice(root: Root, notificationHandler: NotificationHandler) {
        val assetId = root.id ?: return

        getAssetIdCryptoUseCase(assetId).onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    val cryptoData = resource.data
                    handleCryptoPrice(cryptoData, root, notificationHandler)
                }

                is Resource.Loading -> {
                    // Handle loading state if needed
                }

                is Resource.Error -> {
                    // Handle error state, you can log or show a toast message
                    val errorMessage = resource.message ?: "Error fetching crypto data"
                    Log.e("CheckCryptoPrice", errorMessage)
                    // Show a toast message or notification if needed
                    // Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }


    fun handleCryptoPrice(
        cryptoData: RootId?,
        root: Root,
        notificationHandler: NotificationHandler
    ) {
        cryptoData ?: return
        val priceUsd = cryptoData.marketData?.currentPrice?.usd ?: return
        val priceChangePercent = (priceUsd - root.currentPrice!!) / root.currentPrice!! * 100
        if (priceChangePercent > 0.01 || priceChangePercent < -0.01) {
            notificationHandler.showSimpleNotification(
                cryptoData.name ?: "",
                priceChangePercent.toString()
            )
        }
    }

    companion object {
        fun startWork(context: Context) {
            val request = PeriodicWorkRequestBuilder<CryptoPriceCheckWorker>(15, TimeUnit.MINUTES)
                .setInitialDelay(15, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }


}