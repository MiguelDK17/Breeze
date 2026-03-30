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
import androidx.compose.runtime.Composable
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
    Log.d(TAG, "HistoricoDoMesComparativo: entramos em comparativo")
    val context = LocalContext.current
    val comparativoModel = viewModel.comparativoModel.collectAsStateWithLifecycle().value
    val listaDeMovimentacoesMensal = comparativoModel.listaDeMovimentacoesMensal
    Log.d(TAG, "HistoricoDoMesComparativo: variaveis instanciadas")


    when(listaDeMovimentacoesMensal) {
        is UiState.Empty -> {
            ToastManager.showToast(context, "Lista vazia")
        }
        is UiState.Loading -> {
            Log.d(TAG, "HistoricoDoMesComparativo: Carregando")
        }
        is UiState.Error -> {
            val error = listaDeMovimentacoesMensal.exception
            Log.d(TAG, "HistoricoDoMesComparativo: Erro: $error")
        }
        is UiState.Success -> {
            Log.d(TAG, "HistoricoDoMesComparativo: deu success")

            val data = listaDeMovimentacoesMensal.data

            HistoricoDoMesComparativoBody(
                modifier = modifier,
                listMovimentacaoDomain = data.toImmutableList(),
                comparativoModel = comparativoModel,
                setDia = {
                    viewModel.setDia(it)
                },
                converteDiaEmMes = {
                    viewModel.converteDiaEmMes()
                }
            )
        }
    }
}

@Composable
private fun HistoricoDoMesComparativoBody(
    listMovimentacaoDomain: ImmutableList<MovimentacaoDomain>,
    comparativoModel: ComparativoModel,
    modifier: Modifier = Modifier,
    setDia: (String) -> Unit = {},
    converteDiaEmMes : (Boolean) -> Unit = {}

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
            listMovimentacaoDomain = listMovimentacaoDomain,
            comparativoModel = comparativoModel,
            setDia = { setDia(it) },
            converteDiaEmMes = { converteDiaEmMes(it) }
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