package com.migueldk17.breeze.ui.icons

import androidx.compose.ui.graphics.vector.ImageVector

sealed class BreezeIconsType {
    data class ColorIcon(val icon: ImageVector): BreezeIconsType()
    data class LinearIcon(val icon: ImageVector): BreezeIconsType()
}