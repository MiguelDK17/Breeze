package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.utils.MoneyUtils
import java.math.BigDecimal

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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 123.dp)
                        .padding(top = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(0.1.dp, Color.LightGray)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 15.dp,
                                start = 15.dp)
                            .fillMaxWidth()
                    ) {
                    BreezeIcon(
                        breezeIcon = BreezeIcons.Linear.Delivery.GroupLinear,
                        contentDescription = "Icone",
                        modifier = Modifier.size(30.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 15.dp,
                                end = 15.dp
                                )
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //Texto de maior despesa
                            DescriptionText(
                                text = "Maior despesa do mês",
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                size = 13.5.sp,
                                overflow = TextOverflow.Ellipsis,
                                color = NavyBlue
                            )
                            val valorFormatado = BigDecimal.valueOf(458.0).stripTrailingZeros()

                            //Valor da conta
                            TextValue(
                                value = valorFormatado,
                                size = 15.sp,
                                colors = NavyBlue
                            )

                        }
                        //Localização
                        DescriptionText(
                            text = "25/2 - Mercado Central",
                            size = 11.5.sp,
                            color = Color(0xFF5F748F),
                            modifier = Modifier.padding(top = 5.dp),
                        )

                        HighlightProgressBar(
                            modifier = Modifier.padding(top= 12.dp),
                            progress = 0.55f,
                            progressBrush = Brush.horizontalGradient(
                                listOf(
                                    Color(0xFFF3A7B1),
                                    Color(0xFFF09299)
                                )
                            )
                        )
                        //Categoria da conta
                        DescriptionText(
                            text = "Compras e Supermercado",
                            size = 11.sp,
                            color = Color(0xFF5F748F),
                            modifier = Modifier
                                .padding(top = 5.dp)
                        )
                    }
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