package com.example.multimodulecrypto.feature.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.design_system.components.BottomNavigationSample
import com.example.design_system.components.CryptoItem
import com.example.design_system.components.SwipeToRevealItem
import com.example.multimodulecrypto.core.common.DetailScreen
import com.example.multimodulecrypto.core.common.Screen
import com.example.multimodulecrypto.core.model.Root
import com.example.notifaction.CryptoPriceCheckWorker
import java.util.concurrent.TimeUnit

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    val context = LocalContext.current
    val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()


    val request = PeriodicWorkRequestBuilder<CryptoPriceCheckWorker>(15, TimeUnit.MINUTES)
        .setInitialDelay(15, TimeUnit.MINUTES)
        .build()

    LaunchedEffect(true) {
        WorkManager.getInstance(context).enqueue(request)
        viewModel.loadGetCrypto()
    }

    HomeLayer(
        text = homeUiState.text,
        searchList = homeUiState.searchList,
        state = homeUiState.isLoading,
        homeOnClick = {
            navController.navigate(Screen.HomeScreen) {
                popUpTo(Screen.HomeScreen) { inclusive = true }
            }
        },
        favOnClick = {
            navController.navigate(Screen.FavoriteScreen) {
                popUpTo(Screen.HomeScreen) { inclusive = true }
            }
        },
        onValueChange = { viewModel.onValueChange(it) },
        itemOnClick = { navController.navigate(DetailScreen(it)) },
        buttonOnClick = {
            viewModel.saveFav(
                it.id!!,
                it.symbol!!,
                it.name!!,
                it.image!!,
                it.currentPrice.toString(),
                it.priceChangePercentage24h!!
            )
        }

    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeLayer(
    text: String,
    searchList: List<Root>,
    state: Boolean,
    homeOnClick: () -> Unit,
    favOnClick: () -> Unit,
    onValueChange: (String) -> Unit,
    itemOnClick: (String) -> Unit,
    buttonOnClick: (Root) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationSample(
                homeOnClick = homeOnClick,
                favOnClick = favOnClick,
                indexarg = 0
            )
        }, topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.home_screen_title),
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
            TextField(
                value = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                label = { Text(text = stringResource(R.string.home_search)) },
                maxLines = 1,
                shape = RoundedCornerShape(50),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                onValueChange = { onValueChange(it) }
            )


            if (state) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(searchList) {
                        SwipeToRevealItem(
                            content = {
                                CryptoItem(
                                    crypto = it,
                                    onClick = { it.id?.let { it1 -> itemOnClick(it1) } },
                                    true
                                )
                            }, onClick = { buttonOnClick(it) }, true
                        )
                    }
                }
            }
        }
    }
}









