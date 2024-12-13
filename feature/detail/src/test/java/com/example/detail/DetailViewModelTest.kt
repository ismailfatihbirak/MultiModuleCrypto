package com.example.detail

import com.example.domain.GetAssetIdCryptoUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.RootId
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val getAssetIdCryptoUseCase: GetAssetIdCryptoUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(getAssetIdCryptoUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGetCrypto should update uiState with success`() = runTest {
        val assetId = "bitcoin"
        val mockData = RootId()
        coEvery { getAssetIdCryptoUseCase(assetId) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockData))
        }

        viewModel.loadGetCrypto(assetId)

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertEquals(mockData, uiState.cryptos)
        assertEquals("", uiState.error)
    }

    @Test
    fun `loadGetCrypto should update uiState with error`() = runTest {
        val assetId = "invalid"
        val errorMessage = "Error occurred"
        coEvery { getAssetIdCryptoUseCase(assetId) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(errorMessage))
        }

        viewModel.loadGetCrypto(assetId)

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
        assertEquals(RootId(), uiState.cryptos)
    }

    @Test
    fun `loadGetCrypto should update uiState with loading`() = runTest {
        val assetId = "loadingTest"
        coEvery { getAssetIdCryptoUseCase(assetId) } returns flow {
            emit(Resource.Loading())
        }

        viewModel.loadGetCrypto(assetId)

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }
}