package com.migueldk17.breeze.ui.features.historico.utils

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.ui.features.historico.ui.components.retornaValorTotalArredondado
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.DetailsCard
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataTaxaDeJuros
import com.migueldk17.breeze.uistate.UiState
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun ShowDetailsCard(
    onChangeTextoClicado: (Boolean) -> Unit,
    id: Long,
    nameAccount: String,
    valor: Double,
    date: LocalDateTime,
    category: String,
    subCategory: String,
    isContaParcelada: Boolean,
    viewModel: HistoricoDoMesViewModel = hiltViewModel(),


    ){
    var isVisible by remember { mutableStateOf(false) }
    viewModel.buscaParcelaPorId(id)
    val buscaParcela = viewModel.parcela.collectAsStateWithLifecycle().value
    val parcela = when(buscaParcela) {
        is UiState.Loading -> null
        is UiState.Empty -> {
            Log.d(TAG, "DetailsCard: foi retornado um objeto vazio")
            null
        }

        is UiState.Error -> {
            Log.d(TAG, "DetailsCard: Deu erro: ${buscaParcela.exception}")
            null
        }

        is UiState.Success -> {
            buscaParcela.data
        }
    }
    val day = date.dayOfMonth
    val month = date.monthValue
    val year = date.year
    val dataFormatada = "$day/$month/$year"
        val map = if (parcela != null && isContaParcelada) {
            mapOf(
                "Nome" to nameAccount.toString(),
                "Categoria" to category,
                "Sub Categoria" to subCategory,
                "Valor Total" to retornaValorTotalArredondado(parcela.valor, parcela.totalParcelas),
                "Valor da parcela" to formataSaldo(parcela.valor),
                "Data de pagamento" to dataFormatada,
                "Taxa de juros" to "${formataTaxaDeJuros(parcela.porcentagemJuros)} a.m"
            )
        } else {
            mapOf(
                "Nome" to nameAccount.toString(),
                "Categoria" to category,
                "Sub Categoria" to subCategory,
                "Valor Total" to formataSaldo(valor),
                "Data de pagamento" to dataFormatada

            )
        }
        LaunchedEffect(Unit) {
            delay(50)
            isVisible = true
        }
    Log.d(TAG, "ShowDetailsCard: mapa antes de ir pro DetailsCard $map")
        if (isVisible) {
            DetailsCard(map, onChangeOpenDialog = onChangeTextoClicado)
        }


}