package com.example.design_system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.multimodulecrypto.core.common.R
import com.example.multimodulecrypto.core.model.Root

@Composable
fun CryptoItem(crypto: Root, onClick: () -> Unit, pricePercentage: Boolean) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.size(343.dp, 84.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AsyncImage(
                model = crypto.image,
                contentDescription = "",
                modifier = Modifier.size(56.dp)
            )
            Column {
                Box(modifier = Modifier.width(100.dp)) {
                    Text(
                        text = crypto.name.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Text(
                    text = crypto.symbol.toString().uppercase(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp
                )
            }
            Column {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(start = 32.dp)
                ) {
                    Text(
                        text = "$" + crypto.currentPrice.toString().take(7),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                if (pricePercentage){
                    Text(
                        modifier = Modifier.padding(start = 32.dp),
                        text = crypto.priceChangePercentage24h.toString().take(5) + "%",
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        color = if (crypto.priceChangePercentage24h.toString().take(1) == "-") {
                            colorResource(id = R.color.red)
                        } else {
                            colorResource(id = R.color.green)
                        }

                    )
                }
            }
        }
    }

}