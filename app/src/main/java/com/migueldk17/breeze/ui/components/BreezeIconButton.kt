package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIconsType

@Composable
fun BreezeIconButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: BreezeIconsType,
    onCLick: () -> Unit,
    enabled: Boolean = true
){
    IconButton(
        onClick = onCLick,
        modifier = modifier,
        enabled = enabled
    ) {
        BreezeIcon(
            breezeIcon = icon,
            contentDescription = text
        )
        DescriptionText(text)
    }
}