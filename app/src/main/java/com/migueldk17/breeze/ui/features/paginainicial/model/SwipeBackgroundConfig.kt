package com.migueldk17.breeze.ui.features.paginainicial.model

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color

data class SwipeBackgroundConfig(
    val text: String,
    val backgroundColor: Color,
    val arrangementHorizontal: Arrangement.Horizontal,
    val textColor: Color,
    val animationRes: Int?
)