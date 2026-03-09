package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.components.BreezeRegularText
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.NavyBlue
import kotlinx.collections.immutable.persistentListOf
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun DestaquesCard(
    nomeDaConta: String,
    valor: Double,
    category: String,
    icon: BreezeIconsType,
    date: LocalDate,
    progressBush: Brush,
    modifier: Modifier = Modifier,
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
                    breezeIcon = BreezeIcons.Linear.All.NotificationLinear, //Icone de Destaques
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
                        breezeIcon = icon, //Icone de movimentacao
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
                            val valorFormatado = BigDecimal.valueOf(valor).stripTrailingZeros()

                            //Valor da conta
                            TextValue(
                                value = valorFormatado,
                                size = 15.sp,
                                colors = NavyBlue
                            )

                        }
                        val dataFormatada = "${date.dayOfMonth} / ${date.month}"
                        //Localização
                        DescriptionText(
                            text = "$dataFormatada - $nomeDaConta",
                            size = 11.5.sp,
                            color = Color(0xFF5F748F),
                            modifier = Modifier.padding(top = 5.dp),
                        )
                        val progressBushConta = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFF3A7B1),
                                Color(0xFFF09299)
                            )
                        )

                        //Barra de progresso
                        HighlightProgressBar(
                            modifier = Modifier.padding(top= 12.dp),
                            progress = 0.55f,
                            progressBrush = progressBush
                        )

                        //Categoria da conta
                        DescriptionText(
                            text = category,
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

private data class MovimentacaoTeste(
    val nomeDaConta: String,
    val icon: BreezeIconsType,
    val valor: Double,
    val category: String,
    val date: LocalDate,
    val progressBush: Brush
)