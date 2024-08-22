package com.example.network.interceptor


import com.example.network.di.RateLimitState
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RateLimitInterceptor @Inject constructor(
    private val rateLimitState: RateLimitState
) : Interceptor {

    private val requestQueue = mutableListOf<Long>()
    private val maxRequestsPerMinute = 5

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val now = System.currentTimeMillis()
            requestQueue.removeIf { it < now - TimeUnit.MINUTES.toMillis(1) }

            if (requestQueue.size >= maxRequestsPerMinute) {
                rateLimitState.setRateLimitTriggered(true)
            } else {
                rateLimitState.setRateLimitTriggered(false)
            }

            requestQueue.add(now)
        }

        return chain.proceed(chain.request())
    }

    fun triggeredRateLimit(): Boolean {
        return rateLimitState.rateLimitTriggered.value
    }
}

