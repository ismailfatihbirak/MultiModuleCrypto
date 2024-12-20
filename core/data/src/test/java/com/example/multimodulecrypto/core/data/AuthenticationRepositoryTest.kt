package com.example.multimodulecrypto.core.data

import android.content.Context
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationDataSource
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationRepository
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationRepositoryTest {

    private val mockDataSource: AuthenticationDataSource = mockk()
    private val mockContext: Context = mockk(relaxed = true)
    private lateinit var repository: AuthenticationRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AuthenticationRepository(mockDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `emailAuthenticationSignUp should return true on successful sign-up`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"
        coEvery { mockDataSource.emailAuthenticationSignUp(email, password, mockContext) } returns true

        val result = repository.emailAuthenticationSignUp(email, password, mockContext)

        assertEquals(true, result)
        coVerify { mockDataSource.emailAuthenticationSignUp(email, password, mockContext) }
    }

    @Test
    fun `emailAuthenticationSignUp should return false on failed sign-up`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"
        coEvery { mockDataSource.emailAuthenticationSignUp(email, password, mockContext) } returns false

        val result = repository.emailAuthenticationSignUp(email, password, mockContext)

        assertEquals(false, result)
        coVerify { mockDataSource.emailAuthenticationSignUp(email, password, mockContext) }
    }

    @Test
    fun `emailAuthenticationSignIn should return true on successful sign-in`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"
        coEvery { mockDataSource.emailAuthenticationSignIn(email, password, mockContext) } returns true

        val result = repository.emailAuthenticationSignIn(email, password, mockContext)

        assertEquals(true, result)
        coVerify { mockDataSource.emailAuthenticationSignIn(email, password, mockContext) }
    }

    @Test
    fun `emailAuthenticationSignIn should return false on failed sign-in`() = runBlocking {
        val email = "test@example.com"
        val password = "password123"
        coEvery { mockDataSource.emailAuthenticationSignIn(email, password, mockContext) } returns false

        val result = repository.emailAuthenticationSignIn(email, password, mockContext)

        assertEquals(false, result)
        coVerify { mockDataSource.emailAuthenticationSignIn(email, password, mockContext) }
    }
}
