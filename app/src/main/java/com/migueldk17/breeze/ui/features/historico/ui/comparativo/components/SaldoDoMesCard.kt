package com.migueldk17.breeze.ui.features.historico.ui.comparativo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.components.DescriptionText
import com.migueldk17.breeze.ui.components.TitleText
import com.migueldk17.breeze.ui.features.historico.model.LinhaDoTempoModel
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.theme.NavyBlue
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.LocalDateTime


@Composable
fun SaldoDoMesCard(
    modifier: Modifier = Modifier
){
    val optionsLinear = mapOf(
        BreezeIcons.Linear.Money.MoneySend to "Dia",
        BreezeIcons.Linear.Money.MoneyRecive to "Categoria",
        BreezeIcons.Linear.Like.Heart to "Mês"
    )
    val optionsOutlined = mapOf(
        BreezeIcons.Outlined.Money.MoneySend to "Contas",
        BreezeIcons.Outlined.Money.MoneyRecive to "Categoria",
        BreezeIcons.Outlined.All.MoneyRecive to "Mês"
    )
    //Datas fixas para o modelo de teste
    val firstDate = LocalDate.of(2026, 2, 23).atStartOfDay()
    val secondDate = LocalDate.of(2026, 2, 24).atStartOfDay()
    val thirdDate = LocalDate.of(2026, 2, 25).atStartOfDay()
    val fourthDate = LocalDate.of(2026, 2, 26).atStartOfDay()
    val fifthDate = LocalDate.of(2026, 2, 27).atStartOfDay()

    //Modelo de testes que virá a ser substituído por dados do Room
    val graficoDoDiaModel = persistentListOf(
        LinhaDoTempoModel(
            name = "Salário",
            icon = BreezeIcons.Linear.Money.MoneyRecive.enum.name,
            valor = 1000.0,
            colorCard = Color(0xFFACE1C1).toArgb(),
            colorIcon = Color(0xFFACE1C1).toArgb(),
            id = 1,
            dateTime = firstDate,
            isReceita = true
        ),
        LinhaDoTempoModel(
            name = "Mercado",
            icon = BreezeIcons.Linear.Shop.Bag2.enum.name,
            valor = 200.0,
            colorCard = Color(0xFFF69297).toArgb(),
            colorIcon = Color(0xFFF69297).toArgb(),
            id = 2,
            dateTime = secondDate,
            isReceita = false
        ),
        LinhaDoTempoModel(
            name = "Spotify",
            icon = BreezeIcons.Linear.Company.SpotifyLinear.enum.name,
            valor = 23.90,
            colorCard = Color(0xFFF69297).toArgb(),
            colorIcon = Color(0xFFF69297).toArgb(),
            id = 3,
            dateTime = thirdDate,
            isReceita = false
    ),
        LinhaDoTempoModel(
            name = "Compra na Google Play",
            icon = BreezeIcons.Linear.Company.GooglePlayLinear.enum.name,
            valor = 55.90,
            colorCard = Color(0xFFF69297).toArgb(),
            colorIcon = Color(0xFFF69297).toArgb(),
            id = 4,
            dateTime = fourthDate,
            isReceita = false
        ),
        LinhaDoTempoModel(
            name = "Freelancer",
            icon = BreezeIcons.Linear.Money.MoneyRecive.enum.name,
            valor = 200.0,
            colorCard = Color(0xFFACE1C1).toArgb(),
            colorIcon = Color(0xFFACE1C1).toArgb(),
            id = 5,
            dateTime = fifthDate,
            isReceita = true
        )
    )

    //Elevated Card Pai
    BreezeElevatedCard() {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(
                text = "Saldo do mês",
                color = NavyBlue,
                modifier = Modifier
            )
            //Card de Saldo Final
            SaldoFinal()

            //Button Group de opções
            BreezeButtonGroup(
                options = optionsLinear.values.toImmutableList(),
                unCheckedIcons = optionsOutlined.keys.toImmutableList(),
                checkedIcons = optionsLinear.keys.toImmutableList(),
                hasIcon = false,
                onChangeSelectedIndex = {
                }
            )
            GraficoDeBarras(
                modifier = Modifier
                    .height(290.dp),
                graficoDoDiaModel = graficoDoDiaModel
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0xFFACE1C1)
                )
                DescriptionText(
                    text = "Receitas",
                    color = Color(0xFF26595C)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0xFFF69297)
                )
                DescriptionText(
                    text = "Despesas",
                    color = Color(0xFFD3374D)
                )
            }
        }
    }
}
