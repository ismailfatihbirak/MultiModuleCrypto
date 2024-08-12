package com.example.multimodulecrypto.feature.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SignInUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel(){
    private val _uiState = MutableStateFlow(LoginState())
    internal val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private fun signIn(context: Context) {
        signInUseCase(_uiState.value.email, _uiState.value.email, context).onEach {
            when (it) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            auth = it.data ?: false
                        )
                    }
                }
                is Resource.Loading -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            error = it.message ?: "Error"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    internal fun loadSignIn(context: Context) {
        signIn(context)
    }

    internal fun onPasswordChange(newPassword: String){
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword
            )
        }
    }

    internal fun onTfChange(newEmail: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail
            )
        }
    }

    internal fun onToggleShowPassword() {
        _uiState.update { currentState ->
            currentState.copy(
                showPassword = !uiState.value.showPassword
            )
        }
    }
}