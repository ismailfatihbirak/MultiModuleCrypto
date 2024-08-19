package com.example.multimodulecrypto.feature.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetCryptoUseCase
import com.example.domain.SaveFavUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.offlinecache.repository.CacheWorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCryptoUseCase: GetCryptoUseCase,
    private val saveFavUseCase: SaveFavUseCase,
    private val offlineCacheWorkerRepository: CacheWorkerRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
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
                    _state.value = HomeState(error = it.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFavUser(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ) {
        viewModelScope.launch {
            saveFavUseCase(id, symbol, name, image, currentPrice, priceChangePercentage)
        }
    }

    internal fun loadGetCrypto() {
        getCrypto()
    }

    internal fun saveFav(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ) {
        saveFavUser(id, symbol, name, image, currentPrice, priceChangePercentage)
    }
    internal fun startCachingWork(context: Context) {
        offlineCacheWorkerRepository.cacheData(context)
    }
}