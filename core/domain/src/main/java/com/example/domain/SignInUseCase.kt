package com.example.domain

import android.content.Context
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    operator fun invoke(
        email: String,
        password: String,
        context: Context
    ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val auth = authenticationRepository.emailAuthenticationSignIn(email, password, context)
            emit(Resource.Success(auth))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}