package com.example.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.components.BottomNavigationSample
import com.example.design_system.components.CryptoItem
import com.example.design_system.components.SwipeToRevealItem
import com.example.multimodulecrypto.core.common.DetailScreen
import com.example.multimodulecrypto.core.common.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        viewModel.loadGetFavCrypto()
    }
    val state = viewModel.state
    val list = state.value.cryptos


    Scaffold(
        bottomBar = {
            BottomNavigationSample(
                homeOnClick = { navController.navigate(Screen.HomeScreen) },
                favOnClick = { navController.navigate(Screen.FavoriteScreen) },
                indexarg = 1
            )
        }, topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "FAVORITE",
                            modifier = Modifier.align(Alignment.Center),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(list) {
                        SwipeToRevealItem(content = {
                            CryptoItem(
                                crypto = it,
                                onClick = {
                                    navController.navigate(DetailScreen(it.id)) },
                                false
                            )
                        }, onClick = {
                            viewModel.deleteFav(
                                it.symbol!!
                            )
                        }, false)
                    }
                }
            }
        }
    }
}