package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.migueldk17.breezeicons.icons.BreezeIcons
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.ui.components.BreezeButtonGroup
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.DestaquesCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.RedError
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.math.BigDecimal
import java.time.LocalDate

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier
){
    val scroll = rememberScrollState()

    val primeiraData = LocalDate.of( 2026,  2, 8)
    val segundaData = LocalDate.of(2026, 2, 5)

    val primeiraMovimentacao = MovimentacaoTeste(
        nomeDaConta = "Aluguel do Mês",
        icon = BreezeIcons.Linear.Money.MoneySend,
        valor = BigDecimal("650.00"),
        category = "Moradia",
        date = primeiraData,
        progressBush = Brush.horizontalGradient(
            listOf(
                Color(0xFFF3A7B1),
                Color(0xFFF09299)
            )
        )
    )

    val segundaMovimentacao = MovimentacaoTeste(
        nomeDaConta = "Salário",
        icon = BreezeIcons.Linear.Money.MoneyRecive,
        valor = BigDecimal("3500.00"),
        category = "Receita",
        date = segundaData,
        progressBush = Brush.horizontalGradient(
            listOf(
                Color(0xFFB8DECE),
                Color(0xFFB0DAC8)
            )
        )
    )

    val options = persistentListOf(
        "Dia",
        "Categoria",
        "Mês"
    )
    Column(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 464.dp)
            .padding(7.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .background(color = RedError)
            .height(10.dp)
        )

        SaldoDoMesCard()

        GastoCard()

        DestaquesCard(
            nomeDaConta = primeiraMovimentacao.nomeDaConta,
            valor = primeiraMovimentacao.valor,
            category = primeiraMovimentacao.category,
            icon = primeiraMovimentacao.icon,
            date = primeiraMovimentacao.date,
            progressBush = primeiraMovimentacao.progressBush
        )

        DestaquesCard(
            nomeDaConta = segundaMovimentacao.nomeDaConta,
            valor = segundaMovimentacao.valor,
            category = segundaMovimentacao.category,
            icon = segundaMovimentacao.icon,
            date = segundaMovimentacao.date,
            progressBush = segundaMovimentacao.progressBush
        )
    }
}
private data class MovimentacaoTeste(
    val nomeDaConta: String,
    val icon: BreezeIconsType,
    val valor: BigDecimal,
    val category: String,
    val date: LocalDate,
    val progressBush: Brush
)


@Composable
@Preview
private fun Preview(){
    BreezeTheme() {
        HistoricoDoMesComparativo()
    }
}