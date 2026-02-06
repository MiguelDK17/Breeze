package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 14.sp,
    fontWeight: FontWeight? = null,
    color: Color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else Color.Black,
    textAlign: TextAlign? = null,
){
    Text(
        text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontSize = size,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign,
    )
}