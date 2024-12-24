package com.example.domain

import com.example.network.repository.CryptoRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TriggerInterceptorUseCaseTest {

    private lateinit var useCase: TriggerInterceptorUseCase
    private val mockRepository: CryptoRepo = mockk()

    @Before
    fun setUp() {
        useCase = TriggerInterceptorUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit true when repository returns true`() = runTest {
        coEvery { mockRepository.triggerInterceptor() } returns true

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertEquals(1, emissions.size)
        assertTrue(emissions[0])

        coVerify(exactly = 1) { mockRepository.triggerInterceptor() }
    }

    @Test
    fun `invoke should emit false when repository throws an exception`() = runTest {
        coEvery { mockRepository.triggerInterceptor() } throws Exception("Interceptor failed")

        val flow = useCase.invoke()

        val emissions = flow.toList()
        assertEquals(1, emissions.size)
        assertFalse(emissions[0])

        coVerify(exactly = 1) { mockRepository.triggerInterceptor() }
    }
}
