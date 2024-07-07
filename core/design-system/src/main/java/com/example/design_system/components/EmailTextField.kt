package com.example.design_system.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

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
                text = "Email",
                fontSize = 10.sp
            )
        }
    )
}