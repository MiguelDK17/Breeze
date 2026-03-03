package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.DescriptionText

@Composable
fun GastoCard(
    modifier: Modifier = Modifier
){
    var isEllipsisGastoActive by remember { mutableStateOf(true) }
    var isEllipsisCategoria by remember { mutableStateOf(true) }

    ElevatedCard(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 80.dp)
            .padding(5.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lightbulb,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    DescriptionText(
                        text = "Você gastou 15% a mais que mês passado",
                        fontWeight = FontWeight.SemiBold,
                        size = 12.sp,
                        modifier = Modifier
                            .clickable(true, onClick = {
                                isEllipsisGastoActive = !isEllipsisGastoActive
                            })
                            .padding(horizontal = 15.dp),
                        overflow = if (isEllipsisGastoActive) TextOverflow.Ellipsis else TextOverflow.Clip,
                        maxLines = if (isEllipsisGastoActive) 1 else Int.MAX_VALUE
                    )
                    DescriptionText(
                        "Alimentação foi sua maior despesa",
                        size = 12.sp,
                        modifier = Modifier
                            .clickable(true, onClick = {
                                isEllipsisCategoria = !isEllipsisCategoria
                            })
                            .padding(
                                vertical = 5.dp,
                                horizontal = 15.dp),
                        overflow = if (isEllipsisCategoria) TextOverflow.Ellipsis else TextOverflow.Clip,
                        maxLines = if (isEllipsisCategoria) 1 else Int.MAX_VALUE
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.Arrows.AltArrowRight,
                        contentDescription = "Ir para histórico"
                    )
                }

            }
        }
}