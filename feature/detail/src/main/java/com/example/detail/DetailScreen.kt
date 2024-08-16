package com.example.detail

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.multimodulecrypto.core.common.R
import com.example.multimodulecrypto.core.model.Sparkline7d
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel(), id: String) {
    LaunchedEffect(true) {
        viewModel.loadGetCrypto(id)
    }
    val state = viewModel.state
    val crypto = state.value.cryptos
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(com.example.detail.R.string.detail_screen_title),
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
                .verticalScroll(scrollState)
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                ElevatedCard(
                    modifier = Modifier.padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.size(343.dp, 84.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        AsyncImage(
                            model = crypto.image!!.large,
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
                                    text = "$" + crypto.marketData!!.currentPrice!!.usd.toString()
                                        .take(7),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }

                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(com.example.detail.R.string._7day),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 16.sp
                        )
                    }
                    LineChart(
                        sparkline = crypto.marketData!!.sparkline7d!!,
                        lineColor = if (crypto.marketData!!.priceChangePercentage7d.toString()
                                .take(1) == "-"
                        ) {
                            colorResource(id = R.color.red)
                        } else {
                            colorResource(id = R.color.green)
                        },
                        shadowColor = if (crypto.marketData!!.priceChangePercentage7d.toString()
                                .take(1) == "-"
                        ) {
                            colorResource(id = R.color.red).copy(
                                alpha = 0.3f
                            )
                        } else {
                            colorResource(id = R.color.green).copy(
                                alpha = 0.3f
                            )
                        }
                    )
                }
                Text(
                    text = stringResource(com.example.detail.R.string.description),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 16.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(text = crypto.description.toString(), modifier = Modifier.padding(16.dp))
            }
        }

    }

}

@Composable
fun LineChart(
    sparkline: Sparkline7d,
    modifier: Modifier = Modifier,
    lineColor: Color,
    lineWidth: Float = 2f,
    shadowColor: Color
) {
    val prices = sparkline.price
    val maxPrice = prices.maxOrNull() ?: 0.0
    val minPrice = prices.minOrNull() ?: 0.0

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {

        val paddingLeft = 90f // padding to the left to make space for the labels
        val xStep = (size.width - paddingLeft) / (prices.size - 1)
        val path = Path().apply {
            prices.forEachIndexed { index, price ->
                val x = paddingLeft + index * xStep
                val y = ((maxPrice - price) / (maxPrice - minPrice) * size.height).toFloat()
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        val shadowPath = Path().apply {
            prices.forEachIndexed { index, price ->
                val x = paddingLeft + index * xStep
                val y = ((maxPrice - price) / (maxPrice - minPrice) * size.height).toFloat()
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
            lineTo(size.width, size.height)
            lineTo(paddingLeft, size.height)
            close()
        }

        drawPath(path = shadowPath, color = shadowColor)
        drawPath(path = path, color = lineColor, style = Stroke(width = lineWidth))

        val textPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 30f
            isAntiAlias = true
            typeface = android.graphics.Typeface.create(
                android.graphics.Typeface.DEFAULT,
                android.graphics.Typeface.BOLD
            )
        }

        val labelStep = (maxPrice - minPrice) / 4
        for (i in 0..4) {
            val price = minPrice + (labelStep * i)
            val y = ((maxPrice - price) / (maxPrice - minPrice) * size.height).toFloat()
            drawContext.canvas.nativeCanvas.drawText(
                price.roundToInt().toString(),
                0f,
                y,
                textPaint
            )
        }
    }
}


