package com.example.multimodulecrypto.feature.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetCryptoUseCase
import com.example.multimodulecrypto.core.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val getCryptoUseCase : GetCryptoUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<HomeState>(HomeState())
    val state: State<HomeState> = _state
    private fun getCrypto() {
        getCryptoUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = HomeState(cryptos = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)

                }
                is Resource.Error -> {
                    Log.e("axax",it.message.toString())
                    _state.value = HomeState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadGetCrypto() {
        getCrypto()
    }
}