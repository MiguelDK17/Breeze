package com.migueldk17.breeze.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun BreezeButton(
    modifier: Modifier = Modifier,
    text: String,onClick: () -> Unit,
    enabled: Boolean = true
){
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled){
        Text(text,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}