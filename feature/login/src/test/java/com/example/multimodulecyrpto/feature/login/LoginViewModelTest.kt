package com.example.multimodulecyrpto.feature.login

import android.content.Context
import com.example.domain.SignInUseCase
import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.feature.login.LoginState
import com.example.multimodulecrypto.feature.login.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    private val signInUseCase: SignInUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(signInUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signIn success updates uiState with auth=true`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val context = mockk<Context>(relaxed = true)
        val successResponse = true
        viewModel._uiState.value = LoginState(email = email, password = password)

        coEvery {
            signInUseCase(
                email,
                password,
                context
            )
        } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(successResponse))
        }

        viewModel.loadSignIn(context)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(successResponse, uiState.auth)
        assertEquals("", uiState.error)

        verify { signInUseCase(email, password, context) }
    }

    @Test
    fun `onPasswordChange updates password in uiState`() = runTest {
        val newPassword = "newPassword123"
        viewModel.onPasswordChange(newPassword)

        val uiState = viewModel.uiState.first()
        assertEquals(newPassword, uiState.password)
    }

    @Test
    fun `onTfChange updates email in uiState`() = runTest {
        val newEmail = "test@example.com"

        viewModel.onTfChange(newEmail)

        val uiState = viewModel.uiState.first()
        assertEquals(newEmail, uiState.email)
    }

    @Test
    fun `onToggleShowPassword toggles showPassword in uiState`() = runTest {
        val initialShowPassword = viewModel.uiState.first().showPassword

        viewModel.onToggleShowPassword()

        val updatedShowPassword = viewModel.uiState.first().showPassword
        assertEquals(updatedShowPassword, !initialShowPassword)

    }

}