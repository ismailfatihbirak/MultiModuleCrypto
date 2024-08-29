package com.example.multimodulecrypto.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetAllCryptoRoomUseCase
import com.example.domain.GetCryptoUseCase
import com.example.domain.SaveFavUseCase
import com.example.domain.TriggerInterceptorUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.offlinecache.repository.CacheWorkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCryptoUseCase: GetCryptoUseCase,
    private val saveFavUseCase: SaveFavUseCase,
    private val offlineCacheWorkerRepository: CacheWorkerRepository,
    private val getAllCryptoRoomUseCase: GetAllCryptoRoomUseCase,
    private val triggerInterceptorUseCase: TriggerInterceptorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    internal val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        triggerInterceptor()
    }

    private fun getCrypto() {
        getCryptoUseCase().onEach {
            when (it) {
                is Resource.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            cryptos = it.data.orEmpty(),
                            searchList = it.data.orEmpty(),
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

    fun saveFav(
        id: String,
        symbol: String,
        name: String,
        image: String,
        currentPrice: String?,
        priceChangePercentage: Double
    ) {
        saveFavUser(id, symbol, name, image, currentPrice, priceChangePercentage)
    }

    internal fun onValueChange(newText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                text = newText
            )
        }
        searchFilter()
    }

    private fun searchFilter() {
        _uiState.update { currentState ->
            currentState.copy(
                searchList = _uiState.value.cryptos.filter { crypto ->
                    crypto.symbol!!.contains(
                        _uiState.value.text,
                        ignoreCase = true
                    ) || crypto.name!!.contains(
                        _uiState.value.text,
                        ignoreCase = true
                    )
                }
            )
        }
    }

    internal fun startCachingWork(context: Context) {
        offlineCacheWorkerRepository.cacheData(context)
    }

    private fun getCachedCrypto() {
        getAllCryptoRoomUseCase().onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    searchList = it,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun triggerInterceptor() {
        triggerInterceptorUseCase().onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    isTriggered = it
                )
            }
        }.launchIn(viewModelScope)
        if (_uiState.value.isTriggered){
            getCachedCrypto()
        }else{
            loadGetCrypto()
        }
    }

    private fun loadGetCrypto() {
        getCrypto()
    }
}