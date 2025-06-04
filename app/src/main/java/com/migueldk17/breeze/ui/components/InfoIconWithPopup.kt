package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import kotlinx.coroutines.delay


@Composable
fun InfoIconWithPopup(message: String) {
    //Controla a visibilidade do popup
    var showPopup by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = {
            showPopup = true

        }) {
            BreezeIcon(
                BreezeIcons.Linear.Essetional.InfoCircle, contentDescription = "Botão de informação"
            )
        }

        if (showPopup) {
            LaunchedEffect(Unit) {
                delay(2000) //Adiciona um tempo de visibilidade para o popup de 2 segundos
                showPopup = false
            }
            Popup(alignment = Alignment.TopCenter, offset = IntOffset(0, -100)) {
                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(text = message, color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

