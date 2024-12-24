package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.SparklineIn7d
import com.example.network.repository.CryptoRepo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCryptoUseCaseTest {

    private lateinit var useCase: GetCryptoUseCase
    private val mockRepository: CryptoRepo = mockk()

    @Before
    fun setUp() {
        useCase = GetCryptoUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Loading and Success when repository returns data`() = runTest {
        val mockCryptoList = listOf(
            Root(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "https://image.url",
                currentPrice = 50000.0,
                high24h = 51000.0,
                low24h = 49000.0,
                priceChange24h = -1000.0,
                priceChangePercentage24h = -2.0,
                lastUpdated = "2024-12-24T12:00:00Z",
                sparklineIn7d = SparklineIn7d(arrayListOf(50000.0, 50500.0, 49000.0))
            )
        )

        coEvery { mockRepository.getCrypto() } returns mockCryptoList

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals((emissions[1] as Resource.Success).data, mockCryptoList)

        coVerify(exactly = 1) { mockRepository.getCrypto() }
    }

    @Test
    fun `invoke should emit Loading and Error when repository throws exception`() = runTest {
        val mockException = Exception("Failed to fetch data")

        coEvery { mockRepository.getCrypto() } throws mockException

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals((emissions[1] as Resource.Error).message, mockException.message)

        coVerify(exactly = 1) { mockRepository.getCrypto() }
    }
}
