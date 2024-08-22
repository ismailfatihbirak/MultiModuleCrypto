package com.example.offlinecache.repository

import android.content.Context

interface CacheWorkerRepository {
    fun cacheData(context: Context)
}