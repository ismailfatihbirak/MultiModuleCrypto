package com.example.multimodulecrypto.feature.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.components.EmailTextField
import com.example.design_system.components.Logo
import com.example.design_system.components.PasswordTextField
import com.example.design_system.components.SignButton
import com.example.multimodulecrypto.core.common.Screen


@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }
    val context = LocalContext.current

    var emailAuthControl by remember { mutableStateOf(false) }
    emailAuthControl = viewModel.state.value.auth
    var signInCompleted by remember { mutableStateOf(false) }

    if (emailAuthControl && !signInCompleted) {
        signInCompleted = true
        navController.navigate(Screen.HomeScreen){
            popUpTo(Screen.LoginScreen) { inclusive = true }
        }
        Toast.makeText(
            context,
            "sign in successful",
            Toast.LENGTH_SHORT
        ).show()
    }

    Column(
        modifier = Modifier
            .padding(all = 30.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Column {
            EmailTextField(
                tf = email,
                onTfChange = { newTf ->
                    email = newTf
                }
            )
            PasswordTextField(
                password = password,
                onPasswordChange = { newPassword ->
                    password = newPassword
                },
                showPassword = showPassword,
                onToggleShowPassword = {
                    showPassword = !showPassword
                }
            )
        }
        SignButton(
            onClick = {
                viewModel.loadSignIn(email, password, context)
            },
            text = "Log in"
        )
    }
}

