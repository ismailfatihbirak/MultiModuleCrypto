package com.example.domain

import com.example.network.repository.CryptoRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TriggerInterceptorUseCase @Inject constructor(
    private val repository: CryptoRepo
) {
    operator fun invoke(): Flow<Boolean> = flow {
        emit(repository.triggerInterceptor())
    }.catch { e ->
        emit(false)
    }
}
