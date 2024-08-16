package com.example.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetAssetIdCryptoUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.RootId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val getAssetIdCryptoUseCase: GetAssetIdCryptoUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailState())
    internal val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    private fun getCrypto(assetId: String) {
        getAssetIdCryptoUseCase(assetId).onEach {
            when (it) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            cryptos = it.data ?: RootId(),
                            isLoading = false
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

    fun loadGetCrypto(assetId: String) {
        getCrypto(assetId)
    }
}