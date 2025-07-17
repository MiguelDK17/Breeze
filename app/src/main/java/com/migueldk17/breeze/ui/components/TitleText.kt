package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(
    text: String,
    size: TextUnit = 18.sp,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null
){
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = color,
        fontSize = size,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}