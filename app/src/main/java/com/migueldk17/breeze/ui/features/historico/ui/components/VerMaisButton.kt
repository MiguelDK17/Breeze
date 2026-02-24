package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun VerMaisButton(size: Int,expanded: MutableState<Boolean>){
    TextButton(onClick = { expanded.value = !expanded.value }) {
        Icon(
            if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null
        )
        Text(if (expanded.value) "Ver menos" else "Ver mais $size contas...")
    }
}