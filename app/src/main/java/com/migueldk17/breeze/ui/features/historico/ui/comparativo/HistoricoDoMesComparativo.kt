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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.GastoCard
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.components.SaldoDoMesCard
import com.migueldk17.breeze.ui.theme.BreezeTheme
import com.migueldk17.breeze.ui.theme.RedError
import kotlinx.collections.immutable.toImmutableList
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.domain.MovimentacaoDomain
import com.migueldk17.breeze.ui.features.historico.ui.TipoDeDados
import com.migueldk17.breeze.ui.features.historico.ui.comparativo.model.ComparativoModel
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
    val comparativoModel = viewModel.comparativoModel.collectAsStateWithLifecycle().value
    val listaDeMovimentacoesMensal = comparativoModel.listaDeMovimentacoesMensal
    val listaDeMovimentacoesDiaria = comparativoModel.listaDeMovimentacoesDiaria
    val listaDeMovimentacoesCategoria = comparativoModel.listaDeMovimentacoesCategoria

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    val listaSelecionada = when (comparativoModel.tipoDeDados) {
        TipoDeDados.MES -> listaDeMovimentacoesMensal
        TipoDeDados.DIA -> listaDeMovimentacoesDiaria
        TipoDeDados.CATEGORIA -> listaDeMovimentacoesCategoria
    }

    Log.d(TAG, "HistoricoDoMesComparativo:listaSelecionada: $listaSelecionada")
    val mesBackup = viewModel.mes.collectAsStateWithLifecycle().value
    when (state){
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Empty -> {
            Log.d(TAG, "HistoricoDoMesComparativo: Lista vazia")
        }
        is UiState.Success -> {
            val data = state.data
            HistoricoDoMesComparativoBody(
                data = data,
                comparativoModel = comparativoModel,
                mesBackup = mesBackup,
                setDia = { viewModel.setDia(it) },
                setCategoria = { viewModel.setCategoria() },
                converteDiaEmMes = { viewModel.voltarParaMes() },
                modifier = modifier
            )
        }
        is UiState.Error -> {
            val error = state.exception
            Log.d(TAG, "HistoricoDoMesComparativo: erro detectado: $error")
        }
    }

}



@Composable
private fun HistoricoDoMesComparativoBody(
    data: ComparativoData,
    comparativoModel: ComparativoModel,
    mesBackup: String,
    modifier: Modifier = Modifier,
    setDia: (String) -> Unit = {},
    setCategoria: () -> Unit = {},
    converteDiaEmMes : () -> Unit = {},
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

        SaldoDoMesCard(
            data = data,
            comparativoModel = comparativoModel,
            mesBackup = mesBackup,
            setCategoria = setCategoria,
            setDia = setDia,
            converteDiaEmMes = converteDiaEmMes
        )

        GastoCard()

    }
}


@Composable
@Preview
private fun Preview(){
    BreezeTheme() {
        HistoricoDoMesComparativo()
    }
}