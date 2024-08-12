package com.example.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.components.EmailTextField
import com.example.design_system.components.Logo
import com.example.design_system.components.PasswordTextField
import com.example.design_system.components.SignButton
import com.example.multimodulecrypto.core.common.Screen


@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel(), navController: NavController) {
    val context = LocalContext.current
    val signUpUiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (signUpUiState.auth) {
        LaunchedEffect(Unit) {
            viewModel.onNavigateToLoginScreen()
            navController.navigate(Screen.LoginScreen)
            Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
        }
    }

    SignUpLayer(
        email = signUpUiState.email,
        password = signUpUiState.password,
        showPassword = signUpUiState.showPassword,
        onTfChange = {viewModel.onTfChange(it)},
        onPasswordChange = {viewModel.onPasswordChange(it)},
        onToggleShowPassword = viewModel::onToggleShowPassword,
        onClick = { viewModel.loadSignUp(context) })

}

@Composable
private fun SignUpLayer(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    showPassword: Boolean,
    onPasswordChange: (String) -> Unit,
    onTfChange: (String) -> Unit,
    onToggleShowPassword: () -> Unit,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(all = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Column {
            EmailTextField(
                tf = email,
                onTfChange = onTfChange
            )

            PasswordTextField(
                password = password,
                onPasswordChange = onPasswordChange,
                showPassword = showPassword,
                onToggleShowPassword = onToggleShowPassword
            )
        }
        SignButton(onClick = onClick, "Sign Up")
    }
}