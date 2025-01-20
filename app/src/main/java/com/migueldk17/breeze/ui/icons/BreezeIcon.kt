package com.migueldk17.breeze.ui.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BreezeIcon(
    breezeIcon: BreezeIconsType,
    modifier: Modifier = Modifier,
    contentDescription: String?,


    ) {

    val icon: ImageVector = when (breezeIcon) {
        is BreezeIconsType.ColorIcon -> breezeIcon.icon
        is BreezeIconsType.LinearIcon -> breezeIcon.icon
    }

    val tint = if (breezeIcon is BreezeIconsType.ColorIcon){
        Color.Unspecified // Mantém as cores originais
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}