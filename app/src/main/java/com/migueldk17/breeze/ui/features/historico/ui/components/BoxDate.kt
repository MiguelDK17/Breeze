package com.migueldk17.breeze.ui.features.historico.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.migueldk17.breeze.ui.theme.MidnightBlue
import java.time.LocalDate

@Composable
fun BoxDate(date: LocalDate){
    val isSystemInDarkTheme = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .width(81.dp)
            .height(71.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .width(81.dp)
                .height(71.dp)
        ) {
            drawCircle(
                color = if (!isSystemInDarkTheme) Color(0xFFF3F3F3) else MidnightBlue
            )
        }
        Text("${date.dayOfMonth}/${date.month.value}", //Dia do mes e numero do mes
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 20.sp)

    }
}