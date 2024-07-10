@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.multimodulecrypto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.detail.DetailScreen
import com.example.favorite.FavoriteScreen
import com.example.multimodulecrypto.core.common.DetailScreen
import com.example.multimodulecrypto.core.common.Screen
import com.example.multimodulecrypto.feature.home.HomeScreen
import com.example.multimodulecrypto.feature.login.LoginScreen
import com.example.signup.SignUpScreen
import com.example.welcome.WelcomeScreen



@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {
        composable<Screen.WelcomeScreen> {
            WelcomeScreen(navController)
        }
        composable<Screen.SignUpScreen> {
            SignUpScreen(navController = navController)
        }
        composable<Screen.LoginScreen> {
            LoginScreen(navController = navController)
        }
        composable<Screen.HomeScreen> {
            HomeScreen(navController = navController)
        }
        composable<Screen.FavoriteScreen> {
            FavoriteScreen(navController = navController)
        }
        composable<DetailScreen> {
            val args = it.toRoute<DetailScreen>()
            DetailScreen(id = args.assetId.toString())
        }
    }
}

