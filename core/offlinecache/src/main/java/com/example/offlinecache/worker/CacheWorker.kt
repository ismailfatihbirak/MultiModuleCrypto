package com.example.offlinecache.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.GetCryptoUseCase
import com.example.domain.InsertCryptoRoomUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

@HiltWorker
class CacheWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val getCryptoUseCase: GetCryptoUseCase,
    private val insertCryptoRoomUseCase: InsertCryptoRoomUseCase
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                var result: Result = Result.failure()

                getCryptoUseCase()
                    .catch {
                        result = Result.failure()
                    }
                    .collect { cryptoData ->
                        if (cryptoData is Resource.Success) {
                            val cryptoList = cryptoData.data

                            cryptoList?.forEach { crypto ->
                                insertCryptoRoomUseCase(crypto)
                            }
                            result = Result.success()
                        } else {
                            result = Result.failure()
                        }
                    }

                result
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
    }
}







