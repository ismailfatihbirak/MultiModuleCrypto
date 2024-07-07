package com.example.design_system.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp

@Composable
fun SignButton(onClick: () -> Unit,text : String) {
    Button(
        onClick = onClick,colors = ButtonDefaults.buttonColors(
            //containerColor = colorResource(id = R.color.welcome_color),
            contentColor = Color.White
        ), modifier = Modifier.fillMaxWidth(),) {

        Text(text = text, fontSize = 12.sp)
    }
}