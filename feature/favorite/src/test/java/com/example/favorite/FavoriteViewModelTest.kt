package com.example.favorite

import com.example.domain.DeleteFavUseCase
import com.example.domain.GetFavUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
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
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel
    private val getFavUseCase: GetFavUseCase = mockk(relaxed = true)
    private val deleteFavUseCase: DeleteFavUseCase = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = FavoriteViewModel(getFavUseCase, deleteFavUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGetFavCrypto should update uiState with success`() = runTest {
        val mockData: List<Root> = listOf()

        coEvery { getFavUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockData))
        }

        viewModel.loadGetFavCrypto()

        val uiState = viewModel.uiState.value
        assertEquals(false, uiState.isLoading)
        assertEquals(mockData, uiState.cryptos)
        assertEquals("", uiState.error)
    }

    @Test
    fun `loadGetFavCrypto should update uiState with loading`() = runTest {
        coEvery { getFavUseCase() } returns flow {
            emit(Resource.Loading())
        }

        viewModel.loadGetFavCrypto()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `loadGetFavCrypto should update uiState with error`() = runTest {
        val errorMessage = "Error occurred"
        coEvery { getFavUseCase() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(errorMessage))
        }

        viewModel.loadGetFavCrypto()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
    }

    @Test
    fun `deleteFavCrypto should update uiState with success`() = runTest {
        val symbol = "btc"
        val mockData = false
        coEvery { deleteFavUseCase(symbol) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockData))
        }

        viewModel.deleteFav(symbol)

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
        assertEquals(mockData, uiState.delete)
        assertEquals("", uiState.error)
    }

    @Test
    fun `deleteFavCrypto should update uiState with loading`() = runTest {
        val symbol = "btc"
        coEvery { deleteFavUseCase(symbol) } returns flow {
            emit(Resource.Loading())
        }

        viewModel.deleteFav(symbol)

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `deleteFavCrypto should update uiState with error`() = runTest {
        val symbol = "btc"
        val errorMessage = "Error occurred"
        coEvery { deleteFavUseCase(symbol) } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(errorMessage))
        }

        viewModel.deleteFav(symbol)

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
        assertEquals(errorMessage, uiState.error)
    }
}