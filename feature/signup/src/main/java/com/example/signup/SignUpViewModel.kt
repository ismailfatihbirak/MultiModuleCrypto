package com.example.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.SignUpUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpState())
    internal val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()
    private val _uiEvent = MutableSharedFlow<SignUpUiEvent>()

    internal fun onNavigateToLoginScreen() {
        viewModelScope.launch {
            _uiEvent.emit(SignUpUiEvent.NavigateToLoginScreen("Sign up successful"))
        }
    }

    private fun signUp(context: Context) {
        signUpUseCase(_uiState.value.email, _uiState.value.email, context).onEach {
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

    internal fun loadSignUp(context: Context) {
        signUp(context)
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

