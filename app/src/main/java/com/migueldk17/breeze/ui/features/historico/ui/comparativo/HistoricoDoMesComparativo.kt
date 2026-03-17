package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import android.util.Log
import android.content.ContentValues.TAG
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoComparativoViewModel
import com.migueldk17.breeze.ui.utils.ToastManager
import com.migueldk17.breeze.uistate.UiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier,
    viewModel: HistoricoComparativoViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val observeMovimentacao = viewModel.movimentacaoMes.collectAsStateWithLifecycle().value

    val primeiraData = LocalDate.of( 2026,  2, 8)
    val segundaData = LocalDate.of(2026, 2, 5)

    val primeiraMovimentacao = MovimentacaoTeste(
        nomeDaConta = "Transferência Pix",
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

    LaunchedEffect(observeMovimentacao) {
        viewModel.observaContasPoMes()
    }

    when(observeMovimentacao) {
        is UiState.Empty -> {
            ToastManager.showToast(context, "Lista vazia")
        }
        is UiState.Loading -> {
            Log.d(TAG, "HistoricoDoMesComparativo: Carregando")
        }
        is UiState.Error -> {
            val error = observeMovimentacao.exception
            Log.d(TAG, "HistoricoDoMesComparativo: Erro: $error")
        }
        is UiState.Success -> {

            val data = observeMovimentacao.data

            HistoricoDoMesComparativoBody(data.toImmutableList())

        }
    }


}

@Composable
private fun HistoricoDoMesComparativoBody(
    listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>,
    modifier: Modifier = Modifier,
    ){
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .widthIn(min = 389.dp)
            .heightIn(min = 464.dp)
            .padding(7.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .background(color = RedError)
                .height(10.dp)
        )

        SaldoDoMesCard()

        GastoCard()

        quebraValorPositivo(listMovimentacaoDomain)

    }
}

private fun quebraValorPositivo(listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>){
    var valorPositivo: BigDecimal = BigDecimal.ZERO
    val listPositiva = mutableListOf<BigDecimal>()

    for (i in listMovimentacaoDomain) {
        if (i.tipo == TipoMovimentacao.ENTRADA) {
            Log.d(TAG, "quebraValorPositivo: ${i.valor}")
            valorPositivo = i.valor


        }
    }
    listPositiva.add(valorPositivo)
    Log.d(TAG, "quebraValorPositivo: $listPositiva")
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