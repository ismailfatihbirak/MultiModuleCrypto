package com.example.home

import com.example.domain.GetAllCryptoRoomUseCase
import com.example.domain.GetCryptoUseCase
import com.example.domain.SaveFavUseCase
import com.example.domain.TriggerInterceptorUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.feature.home.HomeViewModel
import com.example.offlinecache.repository.CacheWorkerRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    private val getCryptoUseCase: GetCryptoUseCase = mockk(relaxed = true)
    private val saveFavUseCase: SaveFavUseCase = mockk(relaxed = true)
    private val offlineCacheWorkerRepository: CacheWorkerRepository = mockk(relaxed = true)
    private val getAllCryptoRoomUseCase: GetAllCryptoRoomUseCase = mockk(relaxed = true)
    private val triggerInterceptorUseCase: TriggerInterceptorUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
            getCryptoUseCase,
            saveFavUseCase,
            offlineCacheWorkerRepository,
            getAllCryptoRoomUseCase,
            triggerInterceptorUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGetCrypto should update uiState with success`() = runTest {
        val mockData: List<Root> = listOf()

        coEvery { getCryptoUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockData))
        }

        viewModel.loadGetCrypto()

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertEquals(mockData, uiState.cryptos)
        assertEquals(mockData, uiState.searchList)
        assertEquals("", uiState.error)
    }

    @Test
    fun `loadGetCrypto should update uiState with loading`() = runTest {
        coEvery { getCryptoUseCase() } returns flow {
            emit(Resource.Loading())
        }

        viewModel.loadGetCrypto()
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `loadGetCrypto should update uiState with error`() = runTest {
        val errorMessage = "Error occurred"
        coEvery { getCryptoUseCase() } returns flow {
            emit(Resource.Error(errorMessage))
        }

        viewModel.loadGetCrypto()
        advanceUntilIdle()
        val uiState = viewModel.uiState.value
        assertEquals(errorMessage, uiState.error)
    }

    @Test
    fun `saveFav invokes saveFavUseCase with correct parameters`() = runTest {
        val id = "bitcoin"
        val symbol = "btc"
        val name = "Bitcoin"
        val image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
        val currentPrice = "102228"
        val priceChangePercentage = 2.5

        viewModel.saveFav(id, symbol, name, image, currentPrice, priceChangePercentage)
        advanceUntilIdle()
        coVerify {
            saveFavUseCase.invoke(
                eq("bitcoin"),
                eq("btc"),
                eq("Bitcoin"),
                eq("https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"),
                eq("102228"),
                eq(2.5)
            )
        }

    }

    @Test
    fun `onValueChange should update text and trigger searchFilter`() = runTest {
        val newText = "bitcoin"
        viewModel.onValueChange(newText)
        advanceUntilIdle()
        assertEquals(newText, viewModel.uiState.value.text)
    }

    @Test
    fun `searchFilter should filter cryptos by text`() = runTest {
        val cryptos = listOf(
            Root(symbol = "BTC", name = "Bitcoin"),
            Root(symbol = "ETH", name = "Ethereum"),
            Root(symbol = "ADA", name = "Cardano")
        )
        viewModel._uiState.update { it.copy(cryptos = cryptos, text = "bit") }

        viewModel.searchFilter()

        val filteredList = viewModel.uiState.value.searchList
        assertEquals(1, filteredList.size)
        assertEquals("Bitcoin", filteredList[0].name)
    }

    @Test
    fun `getCachedCrypto should update uiState with cached data`() = runTest {
        val cachedData = listOf(
            Root(symbol = "BTC", name = "Bitcoin"),
            Root(symbol = "ETH", name = "Ethereum")
        )
        every { getAllCryptoRoomUseCase.invoke() } returns flowOf(cachedData)

        val job = launch { viewModel.getCachedCrypto() }
        advanceUntilIdle()

        assertEquals(cachedData, viewModel.uiState.value.searchList)
        assertEquals(false, viewModel.uiState.value.isLoading)
        job.cancel()
    }

    @Test
    fun `triggerInterceptor calls getCachedCrypto when isTriggered is true`() =
        runTest(testDispatcher) {
            every { triggerInterceptorUseCase() } returns flowOf(true)
            every { getAllCryptoRoomUseCase() } returns flowOf(emptyList())

            viewModel.triggerInterceptor()
            viewModel.getCachedCrypto()
            advanceUntilIdle()

            assertEquals(true, viewModel.uiState.value.isTriggered)
            verify { getAllCryptoRoomUseCase.invoke() }
        }

    @Test
    fun `triggerInterceptor should update isTriggered and call loadGetCrypto when false`() =
        runTest {
            every { triggerInterceptorUseCase() } returns flowOf(false)
            every { getAllCryptoRoomUseCase() } returns flowOf(emptyList())

            viewModel.triggerInterceptor()
            viewModel.getCachedCrypto()
            advanceUntilIdle()

            assertEquals(false, viewModel.uiState.value.isTriggered)
            verify { getCryptoUseCase.invoke() }
        }
}