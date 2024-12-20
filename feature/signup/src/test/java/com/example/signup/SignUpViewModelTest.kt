package com.example.signup

import android.content.Context
import com.example.domain.SignInUseCase
import com.example.domain.SignUpUseCase
import com.example.multimodulecrypto.core.common.Resource
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
class SignUpViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: SignUpViewModel

    private val signUpUseCase: SignUpUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SignUpViewModel(signUpUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signUp success updates uiState with auth=true`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val context = mockk<Context>(relaxed = true)
        val successResponse = true
        viewModel._uiState.value = SignUpState(email = email, password = password)

        coEvery {
            signUpUseCase(
                email,
                password,
                context
            )
        } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(successResponse))
        }

        viewModel.loadSignUp(context)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(successResponse, uiState.auth)
        assertEquals("", uiState.error)

        verify { signUpUseCase(email, password, context) }
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