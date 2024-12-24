package com.example.network

import com.example.multimodulecrypto.core.model.Description
import com.example.multimodulecrypto.core.model.Image
import com.example.multimodulecrypto.core.model.MarketData
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.RootId
import com.example.multimodulecrypto.core.model.SparklineIn7d
import com.example.network.repository.CryptoDataSource
import com.example.network.repository.CryptoRepo
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class CryptoRepoTest {

    private lateinit var repository: CryptoRepo
    private val mockDataSource: CryptoDataSource = mockk()

    @Before
    fun setUp() {
        repository = CryptoRepo(mockDataSource)
    }

    @Test
    fun `getCrypto should return a list of Root`() = runTest {
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

        coEvery { mockDataSource.getCrypto() } returns mockCryptoList

        val result = repository.getCrypto()

        assertEquals(mockCryptoList, result)
        coVerify(exactly = 1) { mockDataSource.getCrypto() }
    }

    @Test
    fun `getAssetIdCrypto should return a RootId`() = runTest {
        val mockAssetId = "bitcoin"
        val mockRootId = RootId(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            hashingAlgorithm = "SHA-256",
            description = Description("Bitcoin is a cryptocurrency."),
            image = Image("https://image.url"),
            marketData = MarketData()
        )

        coEvery { mockDataSource.getAssetIdCrypto(mockAssetId) } returns mockRootId

        val result = repository.getAssetIdCrypto(mockAssetId)

        assertEquals(mockRootId, result)
        coVerify(exactly = 1) { mockDataSource.getAssetIdCrypto(mockAssetId) }
    }

    @Test
    fun `triggerInterceptor should return true when triggered`() = runTest {
        coEvery { mockDataSource.triggerInterceptor() } returns true

        val result = repository.triggerInterceptor()

        assertTrue(result)
        coVerify(exactly = 1) { mockDataSource.triggerInterceptor() }
    }

    @Test
    fun `triggerInterceptor should return false when not triggered`() = runTest {
        coEvery { mockDataSource.triggerInterceptor() } returns false

        val result = repository.triggerInterceptor()

        assertFalse(result)
        coVerify(exactly = 1) { mockDataSource.triggerInterceptor() }
    }
}
