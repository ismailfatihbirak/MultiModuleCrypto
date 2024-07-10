package com.example.design_system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.multimodulecrypto.core.common.R

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToRevealItem(content: @Composable () -> Unit, onClick: () -> Unit, iconControl: Boolean) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val size = with(LocalDensity.current) { 20.dp.toPx() }
    val anchors =
        mapOf(0f to 0, -size to 1)


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.9f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(60.dp)
                .align(Alignment.CenterEnd)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = if (iconControl) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.Delete
                        },
                        contentDescription = null,
                        tint = if (iconControl) {
                            colorResource(id = R.color.green)
                        } else {
                            colorResource(id = R.color.red)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = swipeableState.offset.value.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            content()
        }
    }
}