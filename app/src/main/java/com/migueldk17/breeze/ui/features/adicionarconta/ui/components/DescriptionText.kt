package com.migueldk17.breeze.ui.features.adicionarconta.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionText(text: String, size: TextUnit = 14.sp){
    Text(
        text,
        style = MaterialTheme.typography.bodySmall,
        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onSurface else Color.Black,
        fontSize = size
    )
}