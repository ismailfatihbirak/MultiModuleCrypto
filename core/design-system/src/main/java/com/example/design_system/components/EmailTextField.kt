package com.example.design_system.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.multimodulecrypto.design_system.R

@Composable
fun EmailTextField(
    tf: String,
    onTfChange: (String) -> Unit
) {
    OutlinedTextField(
        value = tf,
        onValueChange = onTfChange,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = stringResource(R.string.email),
                fontSize = 10.sp
            )
        }
    )
}