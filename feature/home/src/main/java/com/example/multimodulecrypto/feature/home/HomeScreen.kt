package com.example.multimodulecrypto.feature.home

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.design_system.components.BottomNavigationSample
import com.example.design_system.components.CryptoItem
import com.example.design_system.components.SwipeToRevealItem
import com.example.multimodulecrypto.core.common.DetailScreen
import com.example.multimodulecrypto.core.common.Screen
import com.example.notifaction.CryptoPriceCheckWorker.Companion.startWork

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    LaunchedEffect(true) {
        viewModel.loadGetCrypto()
    }
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }
    val state = viewModel.state
    val searchList = state.value.cryptos.filter { crypto ->
        crypto.symbol!!.contains(text, ignoreCase = true) || crypto.name!!.contains(
            text,
            ignoreCase = true
        )
    }

    Scaffold(
        bottomBar = {
            BottomNavigationSample(
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
                            text = "CryptoRUIS",
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
                label = { Text(text = "Search") },
                maxLines = 1,
                shape = RoundedCornerShape(50),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                onValueChange = { newText ->
                    text = newText
                    if (newText.isNotBlank()) {
                        text = newText
                    }
                })


            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn {
                    items(searchList) {
                        SwipeToRevealItem(content = {
                            CryptoItem(
                                crypto = it,
                                onClick = { navController.navigate(DetailScreen(it.id)) },
                                true
                            )
                        }, onClick = {
                            viewModel.saveFav(
                                it.id!!,
                                it.symbol!!,
                                it.name!!,
                                it.image!!,
                                it.currentPrice.toString(),
                                it.priceChangePercentage24h!!
                            )
                            startWork(context = context)
                            //workmanager çağırmayı buraya ekle
                        }, true)
                    }
                }
            }
        }
    }
}









