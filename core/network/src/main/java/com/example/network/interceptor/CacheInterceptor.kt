package com.example.network.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code() == 429) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.MINUTES)
                .build()

            val cachedRequest = request.newBuilder()
                .cacheControl(cacheControl)
                .build()

            val cachedResponse = chain.proceed(cachedRequest)
            if (cachedResponse.code() == 200) {
                return cachedResponse
            }
        }

        return response
    }
}






