package com.migueldk17.breeze.ui.icons

import android.content.ContentValues.TAG
import android.util.Log
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
    color: Color = Color.Unspecified
    ) {

    val icon: ImageVector = when (breezeIcon) {
        is BreezeIconsType.ColorIcon -> breezeIcon.icon
        is BreezeIconsType.LinearIcon -> breezeIcon.icon
    }

    val tint = when {
        breezeIcon is BreezeIconsType.ColorIcon -> {
            Color.Unspecified // Mantém as cores originais
        }
        breezeIcon is BreezeIconsType.LinearIcon && color != Color.Unspecified -> {
            color
        }
        else -> {
            MaterialTheme.colorScheme.onSurface
        }

    }


    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}