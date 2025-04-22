package com.migueldk17.breeze.ui.features.historico.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun BoxDate(date: LocalDateTime){
    Box(
        modifier = Modifier
            .width(71.dp)
            .height(71.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .width(71.dp)
                .height(71.dp)
        ) {
            drawCircle(
                color = Color(0xFFF3F3F3)
            )
        }
        Text("${date.dayOfMonth}/${date.month.value}",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp)

    }
}