package com.example.multimodulecrypto.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.multimodulecrypto.core.model.Root

@Composable
fun HomeRoute(userId: String) {
    HomeScreen(userId = userId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(userId: String, viewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(true) {
        viewModel.loadGetCrypto()
    }
    var text by remember { mutableStateOf("") }
    val state = viewModel.state
    val searchList = state.value.cryptos.filter { crypto ->
        crypto.symbol!!.contains(text, ignoreCase = true) || crypto.name!!.contains(text, ignoreCase = true)}
    Column (

    ){
        TextField(
            value = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            label = { Text(text = "Search")},
            maxLines = 1,
            shape = RoundedCornerShape(50),
            leadingIcon = { Icon(imageVector = Icons.Default.Search,
                contentDescription = null) },
            onValueChange ={ newText->
                text = newText
                if (newText.isNotBlank()){
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
            LazyColumn{
                items(searchList){
                    CryptoItem(crypto = it)
                }
            }
        }
    }
}

@Composable
fun CryptoItem(crypto: Root) {
    //val str = crypto.id_icon
    //val result = str?.replace("-", "")
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
//            AsyncImage(
//                model = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/${result}.png",
//                contentDescription = "",
//                modifier = Modifier.size(64.dp)
//            )
            Column {
                Box(modifier = Modifier.width(140.dp)) {
                    Text(
                        text = crypto.symbol.toString().uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = crypto.name.toString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
            Column {
                Box(modifier = Modifier.width(100.dp)) {
                    Text(
                        text = "$"+crypto.currentPrice.toString().take(7),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

}

