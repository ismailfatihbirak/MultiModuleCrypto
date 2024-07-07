@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.multimodulecrypto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.detail.DetailScreen
import com.example.favorite.FavoriteScreen
import com.example.multimodulecrypto.feature.home.HomeScreen
import com.example.multimodulecrypto.feature.login.LoginScreen
import com.example.signup.SignUpScreen
import com.example.welcome.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreen

@Serializable
object SignUpScreen
@Serializable
object LoginScreen

@Serializable
object HomeScreen
@Serializable
object FavoriteScreen

@Serializable
object DetailScreen
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WelcomeScreen
    ) {
        composable<WelcomeScreen> {
            WelcomeScreen(onClick = {navController.navigate(SignUpScreen)})
        }
        composable<SignUpScreen> {
            SignUpScreen()
        }
        composable<LoginScreen> {
            LoginScreen(onClick = {navController.navigate(HomeScreen)})
        }
        composable<HomeScreen> {
            HomeScreen()
        }
        composable<FavoriteScreen> {
            FavoriteScreen()
        }
        composable<DetailScreen> {
            DetailScreen()
        }
    }
}
