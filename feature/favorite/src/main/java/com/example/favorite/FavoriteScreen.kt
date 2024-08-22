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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.design_system.components.BottomNavigationSample
import com.example.design_system.components.CryptoItem
import com.example.design_system.components.SwipeToRevealItem
import com.example.multimodulecrypto.core.common.DetailScreen
import com.example.multimodulecrypto.core.common.Screen
import com.example.multimodulecrypto.core.model.Root

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel = hiltViewModel()) {
    val favUiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteLayer(
        favList = favUiState.cryptos,
        isLoading = favUiState.isLoading,
        homeOnClick = {
            navController.navigate(Screen.HomeScreen) {
                popUpTo(Screen.FavoriteScreen) { inclusive = true }
            }
        },
        favOnClick = {
            navController.navigate(Screen.FavoriteScreen) {
                popUpTo(Screen.FavoriteScreen) { inclusive = true }
            }
        },
        itemOnClick = {
            navController.navigate(DetailScreen(it))
        },
        deleteOnClick = {
            viewModel.deleteFav(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoriteLayer(
    modifier: Modifier = Modifier,
    favList: List<Root>,
    isLoading: Boolean,
    homeOnClick: () -> Unit,
    favOnClick: () -> Unit,
    itemOnClick: (String) -> Unit,
    deleteOnClick: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationSample(
                homeOnClick = homeOnClick,
                favOnClick = favOnClick,
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
                            text = stringResource(R.string.favorite_screen_title),
                            modifier = Modifier.align(Alignment.Center),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(favList) {
                        SwipeToRevealItem(content = {
                            CryptoItem(
                                crypto = it,
                                onClick = { it.id?.let { it1 -> itemOnClick(it1) } },
                                false
                            )
                        }, onClick = { it.symbol?.let { it1 -> deleteOnClick(it1) } }, false)
                    }
                }
            }
        }
    }
}