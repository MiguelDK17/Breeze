package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.GradientCard
import com.migueldk17.breeze.ui.theme.GreenSuccess
import com.migueldk17.breeze.ui.theme.NavyBlue
import com.migueldk17.breeze.ui.theme.RedError
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SaldoFinal(
    modifier: Modifier = Modifier
){
    val listColors = persistentListOf(
        Color(0XFFE9F1FC),
        Color(0xFFF2F6FD)
    )

    val brushColor = Brush.verticalGradient(
        colors = listColors
    )

    //Card do saldo final
    GradientCard(
        modifier =  modifier
            .widthIn(min = 360.dp)
            .heightIn(min = 126.dp)
            .padding(5.dp),
        gradientColors = listColors,
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            //Row de receitas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 7.dp,
                        horizontal = 10.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                DescriptionText(
                    text = "Receitas"
                )

                DescriptionText(
                    text = "R$ 3.200",
                    fontWeight = FontWeight.SemiBold,
                    color = NavyBlue
                )
            }
            //Row de despesas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DescriptionText(
                    text = "Despesas"
                )
                DescriptionText(
                    text = "R$ 2.740",
                    fontWeight = FontWeight.SemiBold,
                    color = RedError
                )
            }
            Spacer(Modifier.height(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 5.dp,
                            horizontal = 10.dp,
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DescriptionText(
                        text = "Saldo Final"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Icone de saldo final",
                            tint = GreenSuccess,
                            modifier = Modifier.size(14.dp)
                        )
                        DescriptionText(
                            text = "R$ 2.740",
                            fontWeight = FontWeight.SemiBold,
                            color = GreenSuccess
                        )

                    }
                }
            }
        }
    }

}
