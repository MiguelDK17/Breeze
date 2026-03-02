package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.DescriptionText

@Composable
fun GastoCard(
    modifier: Modifier = Modifier
){
    ElevatedCard(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 80.dp, max = 90.dp)
            .padding(5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Lightbulb,
                contentDescription = null
            )
            Column() {
                Row(
                ) {
                    DescriptionText(
                        text = "Você gastou 15%",
                        fontWeight = FontWeight.SemiBold,
                        size = 12.sp
                    )
                    DescriptionText(
                        "a mais que mês passado",
                        size = 12.sp
                    )
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.Arrows.AltArrowRight,
                        contentDescription = "Ir para histórico"
                    )
                }
                DescriptionText(
                    "Alimentação foi sua maior despesa",
                    size = 12.sp
                )
            }

        }
    }
}