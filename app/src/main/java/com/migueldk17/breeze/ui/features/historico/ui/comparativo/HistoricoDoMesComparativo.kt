package com.migueldk17.breeze.ui.features.historico.ui.comparativo

import android.util.Log
import android.content.ContentValues.TAG
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.RedError
import kotlinx.collections.immutable.toImmutableList
import java.math.BigDecimal
import java.time.LocalDate
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.enums.TipoComparacao
import com.migueldk17.breeze.enums.TipoMovimentacao
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoComparativoViewModel
import com.migueldk17.breeze.ui.utils.ToastManager
import com.migueldk17.breeze.ui.utils.formatarValorEmReal
import com.migueldk17.breeze.uistate.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HistoricoDoMesComparativo(
    modifier: Modifier = Modifier,
    viewModel: HistoricoComparativoViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val observeMovimentacao = viewModel.movimentacaoMes.collectAsStateWithLifecycle().value


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

            HistoricoDoMesComparativoBody(
                modifier = modifier,
                listMovimentacaoDomain = data.toImmutableList(),
                setDia = {
                    viewModel.setDia(it)
                }

            )

        }
    }


}

@Composable
private fun HistoricoDoMesComparativoBody(
    listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>,
    modifier: Modifier = Modifier,
    setDia: (String) -> Unit = {},

    ){
    val scroll = rememberScrollState()
    val listRetornaValores = retornaValoresFinais(listMovimentacaoDomain, context = LocalContext.current)

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

        SaldoDoMesCard(
            totalDeReceitas = listRetornaValores[0],
            totalDeDespesas = listRetornaValores[1],
            saldoFinal = listRetornaValores[2],
            listMovimentacaoDomain = listMovimentacaoDomain,
            setDia = { setDia(it) }
        )

        GastoCard()

    }
}


private fun retornaValoresFinais(listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>, context: Context): ImmutableList<String>{
    val listPositiva = mutableListOf<BigDecimal>()
    val listNegativa = mutableListOf<BigDecimal>()

    for (i in listMovimentacaoDomain) {
        if (i.tipo == TipoMovimentacao.ENTRADA) listPositiva.add(i.valor) else listNegativa.add(i.valor)

    }
    Log.d(TAG, "quebraValorPositivo: Lista cheia: $listPositiva")

    val totalEntradas = listPositiva.sumOf { it }
    val totalSaidas = listNegativa.sumOf { it }
    val valorTotal = totalEntradas + totalSaidas


    val totalEntradasEmReais = totalEntradas.formatarValorEmReal()
    val totalSaidasEmReais = totalSaidas.formatarValorEmReal()
    val valorTotalEmReais = valorTotal.formatarValorEmReal()

    Log.d(TAG, "retornaValoresFinais: $totalEntradasEmReais")
    Log.d(TAG, "retornaValoresFinais: $totalSaidasEmReais")
    Log.d(TAG, "retornaValoresFinais: $valorTotalEmReais")


    val listaFinal = persistentListOf<String>(totalEntradasEmReais, totalSaidasEmReais, valorTotalEmReais)
    return listaFinal



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