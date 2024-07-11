package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class RateLimitInterceptor : Interceptor {
    private val requestQueue = mutableListOf<Long>()
    private val maxRequestsPerMinute = 5

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val now = System.currentTimeMillis()
            requestQueue.removeIf { it < now - TimeUnit.MINUTES.toMillis(1) }

            if (requestQueue.size >= maxRequestsPerMinute) {
                val waitTime = TimeUnit.MINUTES.toMillis(1) - (now - requestQueue[0])
                Thread.sleep(waitTime)
            }

            requestQueue.add(now)
        }

        return chain.proceed(chain.request())
    }
}
