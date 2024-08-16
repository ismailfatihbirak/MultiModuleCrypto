package com.example.favorite

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavUseCase
import com.example.domain.GetFavUseCase
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
class FavoriteViewModel @Inject constructor(
    private val getFavUseCase: GetFavUseCase,
    private val deleteFavUseCase: DeleteFavUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteState())
    internal val uiState: StateFlow<FavoriteState> = _uiState.asStateFlow()

    init {
        getFavCrypto()
    }

    private fun getFavCrypto() {
        getFavUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            cryptos = it.data.orEmpty(),
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
                    Log.e("axax", it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteFavCrypto(symbol: String) {
        deleteFavUseCase(symbol).onEach {
            when (it) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            delete = it.data ?: false
                        )
                    }
                    getFavCrypto()
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

    fun deleteFav(symbol: String) {
        deleteFavCrypto(symbol)
    }
}