package com.example.offlinecache.repository

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.offlinecache.worker.CacheWorker
import java.util.concurrent.TimeUnit

class CacheWorkerRepositoryImpl : CacheWorkerRepository {
    override fun cacheData(context: Context) {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<CacheWorker>(
            15, TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "UniqCacheWork",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )

    }
}