package com.example.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavUseCase
import com.example.domain.GetFavUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavUseCase: GetFavUseCase,
    private val deleteFavUseCase: DeleteFavUseCase
) : ViewModel() {

    private val _state = mutableStateOf<FavoriteState>(FavoriteState())
    val state: State<FavoriteState> = _state

    private fun getFavCrypto(){
        getFavUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = FavoriteState(cryptos = it.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = FavoriteState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = FavoriteState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteFavCrypto(symbol: String) {
        deleteFavUseCase(symbol).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = FavoriteState(delete = it.data ?: false)
                    loadGetFavCrypto()
                }

                is Resource.Loading -> {
                    _state.value = FavoriteState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = FavoriteState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadGetFavCrypto() {
        getFavCrypto()
    }

    fun deleteFav(symbol: String) {
        deleteFavCrypto(symbol)
    }
}