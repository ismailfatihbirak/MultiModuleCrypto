package com.example.multimodulecrypto.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val context = LocalContext.current
    val signInUiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (signInUiState.auth) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.HomeScreen)
            Toast.makeText(context,
                context.getString(R.string.sign_in_successful), Toast.LENGTH_SHORT).show()
        }
    }

    LoginLayer(
        email = signInUiState.email,
        password = signInUiState.password,
        showPassword = signInUiState.showPassword,
        onTfChange = {viewModel.onTfChange(it)},
        onPasswordChange = {viewModel.onPasswordChange(it)},
        onToggleShowPassword = viewModel::onToggleShowPassword,
        onClick = { viewModel.loadSignIn(context) })

}

@Composable
private fun LoginLayer(
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
        SignButton(
            onClick = onClick,
            text = stringResource(R.string.log_in)
        )
    }
}