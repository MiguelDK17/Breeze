package com.migueldk17.breeze.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun InfoIconWithTooltip(message: String) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.TopCenter) {
        IconButton(onClick = {
            showTooltip = true

        }) {
            // Oculta depois de 2 segundos
            LaunchedEffect(Unit) {
                delay(2000)
                showTooltip = false
            }
            Icon(
                Icons.Default.Info,
                contentDescription = "Informações",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        AnimatedVisibility(visible = showTooltip) {
            Box(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .background(
                        color = Color(0xFF333333),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}
