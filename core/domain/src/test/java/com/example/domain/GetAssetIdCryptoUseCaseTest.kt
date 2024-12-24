package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.model.Description
import com.example.multimodulecrypto.core.model.Image
import com.example.multimodulecrypto.core.model.MarketData
import com.example.multimodulecrypto.core.model.RootId
import com.example.network.repository.CryptoRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAssetIdCryptoUseCaseTest {

    private lateinit var useCase: GetAssetIdCryptoUseCase
    private val mockRepository: CryptoRepo = mockk()

    @Before
    fun setUp() {
        useCase = GetAssetIdCryptoUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Loading and Success when repository returns data`() = runTest {
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

        coEvery { mockRepository.getAssetIdCrypto(mockAssetId) } returns (mockRootId)

        val flow = useCase.invoke(mockAssetId)

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals((emissions[1] as Resource.Success).data, mockRootId)

        coVerify(exactly = 1) { mockRepository.getAssetIdCrypto(mockAssetId) }
    }

    @Test
    fun `invoke should emit Loading and Error when repository throws exception`() = runTest {
        val mockAssetId = "invalid_id"
        val mockException = Exception("Asset not found")

        coEvery { mockRepository.getAssetIdCrypto(mockAssetId) } throws mockException

        val flow = useCase.invoke(mockAssetId)

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals((emissions[1] as Resource.Error).message, mockException.message)

        coVerify(exactly = 1) { mockRepository.getAssetIdCrypto(mockAssetId) }
    }
}
