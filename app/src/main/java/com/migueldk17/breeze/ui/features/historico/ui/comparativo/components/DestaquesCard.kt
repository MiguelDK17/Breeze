package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun DestaquesCard(
    modifier: Modifier = Modifier
){
    BreezeElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BreezeIcon(
                    breezeIcon = BreezeIcons.Linear.All.NotificationLinear,
                    contentDescription = null
                )
                BreezeRegularText(
                    text = "Destaques",
                    modifier = Modifier
                        .padding(start = 10.dp),
                    size = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .padding(
                        top = 15.dp,
                        start = 10.dp)
                    .fillMaxWidth()
            ) {
                BreezeIcon(
                    breezeIcon = BreezeIcons.Linear.Delivery.GroupLinear,
                    contentDescription = "Icone",
                    modifier = Modifier

                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp)
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DescriptionText(
                            text = "Maior despesa do mês",
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        TextValue(
                            value = 458.0
                        )

                    }
                    DescriptionText(
                        text = "25/2 - Mercado Central"
                    )

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    DescriptionText(
                        text = "Compras e Supermercado"
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview(){
    BreezeTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DestaquesCard()
        }
    }
}