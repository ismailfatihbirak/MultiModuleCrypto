package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.SparklineIn7d
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetFavUseCaseTest {

    private lateinit var useCase: GetFavUseCase
    private val mockRepository: FirestoreRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetFavUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Loading and Success when repository returns data`() = runTest {
        val mockFavList = listOf(
            Root(
                id = "ethereum",
                symbol = "eth",
                name = "Ethereum",
                image = "https://image.url",
                currentPrice = 4000.0,
                high24h = 4100.0,
                low24h = 3900.0,
                priceChange24h = -50.0,
                priceChangePercentage24h = -1.2,
                lastUpdated = "2024-12-24T12:00:00Z",
                sparklineIn7d = SparklineIn7d(arrayListOf(4000.0, 4050.0, 3900.0))
            )
        )

        coEvery { mockRepository.getFavList() } returns mockFavList

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals((emissions[1] as Resource.Success).data, mockFavList)

        coVerify(exactly = 1) { mockRepository.getFavList() }
    }

    @Test
    fun `invoke should emit Loading and Error when repository throws exception`() = runTest {
        val mockException = Exception("Failed to fetch favorites")

        coEvery { mockRepository.getFavList() } throws mockException

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals((emissions[1] as Resource.Error).message, mockException.message)

        coVerify(exactly = 1) { mockRepository.getFavList() }
    }
}
