package com.example.domain

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import android.content.Context
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationRepository

@ExperimentalCoroutinesApi
class SignInUseCaseTest {

    private lateinit var useCase: SignInUseCase
    private val mockRepository: AuthenticationRepository = mockk()
    private val mockContext: Context = mockk()

    @Before
    fun setUp() {
        useCase = SignInUseCase(mockRepository)
    }

    @Test
    fun `invoke should emit Loading and Success when sign-in is successful`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val expectedResult = true

        coEvery { mockRepository.emailAuthenticationSignIn(email, password, mockContext) } returns expectedResult

        val flow = useCase.invoke(email, password, mockContext)

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals((emissions[1] as Resource.Success).data, expectedResult)

        coVerify(exactly = 1) { mockRepository.emailAuthenticationSignIn(email, password, mockContext) }
    }

    @Test
    fun `invoke should emit Loading and Error when sign-in fails`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val exceptionMessage = "Authentication failed"

        coEvery { mockRepository.emailAuthenticationSignIn(email, password, mockContext) } throws Exception(exceptionMessage)

        val flow = useCase.invoke(email, password, mockContext)

        val emissions = flow.toList()
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals((emissions[1] as Resource.Error).message, exceptionMessage)

        coVerify(exactly = 1) { mockRepository.emailAuthenticationSignIn(email, password, mockContext) }
    }
}