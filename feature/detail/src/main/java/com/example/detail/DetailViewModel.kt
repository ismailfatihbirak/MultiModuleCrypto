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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val getAssetIdCryptoUseCase: GetAssetIdCryptoUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<DetailState>(DetailState())
    val state: State<DetailState> = _state
    private fun getCrypto(assetId: String) {
        getAssetIdCryptoUseCase(assetId).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = DetailState(cryptos = it.data ?: RootId())
                }
                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)

                }
                is Resource.Error -> {
                    _state.value = DetailState(error = it.message ?: "Error")
                    Log.e("axax1",it.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadGetCrypto(assetId: String) {
        getCrypto(assetId)
    }
}