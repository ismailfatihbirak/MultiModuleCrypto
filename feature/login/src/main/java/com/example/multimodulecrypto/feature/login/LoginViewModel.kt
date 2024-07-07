package com.example.multimodulecrypto.feature.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SignInUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel(){

    private val _state = mutableStateOf<LoginState>(LoginState())
    val state: State<LoginState> = _state

    private fun signIn(email:String,password:String,context: Context) {
        signInUseCase(email, password, context).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = LoginState(auth = it.data ?: false)
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadSignIn(email:String,password:String,context: Context) {
        signIn(email, password, context)
    }
}