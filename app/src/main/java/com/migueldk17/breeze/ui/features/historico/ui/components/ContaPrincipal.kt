package com.migueldk17.breeze.ui.features.historico.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.migueldk17.breezeicons.icons.BreezeIcon
import com.github.migueldk17.breezeicons.icons.BreezeIconsType
import com.migueldk17.breeze.converters.toLocalDate
import com.migueldk17.breeze.entity.ParcelaEntity
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.paginainicial.ui.components.DetailsCard
import com.migueldk17.breeze.ui.utils.arredondarValor
import com.migueldk17.breeze.ui.utils.formataSaldo
import com.migueldk17.breeze.ui.utils.formataTaxaDeJuros
import com.migueldk17.breeze.ui.utils.formataValorConta
import com.migueldk17.breeze.uistate.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun ContaPrincipal(
    date: LocalDate, //Data de criação da conta
    nameAccount: String, //Nome da conta
    breezeIcon: BreezeIconsType, //Icone BreezeIcon
    price: Double, //Valor da conta
    id: Long,
    viewModel: HistoricoDoMesViewModel= hiltViewModel()

){
    var textoClicado by remember {mutableStateOf(false)}


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            "-",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 24.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp)
        ) {

            BoxDate(date) //Box de data
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(71.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BreezeIcon(breezeIcon = breezeIcon,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .size(25.dp))
            Text(
                nameAccount,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 15.sp,
                modifier = Modifier
                    .weight(1f) //Adiciona peso ao Text
                    .padding(end = 8.dp)
                    .clickable{
                        textoClicado = true
                    }
                    ,
                overflow = TextOverflow.Ellipsis, //Caso o texto seja grande demais coloca ... no final
                maxLines = 1 //Limita o texto a 1 linha para evitar quebra

            )

            Text(
                formataSaldo(price),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp),
                textAlign = TextAlign.End
            )

        }
    }

    if (textoClicado){
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
                Log.d(TAG, "DetailsCard: tem algum erro que não tá sendo captado")
                buscaParcela.data
            }
        }
        val day = date.dayOfMonth
        val month = date.monthValue
        val year = date.year
        val dataFormatada = "$day/$month/$year"
        val map = if (parcela != null){
            mapOf(
                "Nome:" to nameAccount.toString(),
                "Valor Total:" to retornaValorTotalArredondado(parcela.valor, parcela.totalParcelas),
                "Valor da parcela:" to formataSaldo(parcela.valor),
                "Data de pagamento:" to dataFormatada,
                "Taxa de juros:" to "${formataTaxaDeJuros(parcela.porcentagemJuros)} a.m"
            )
        } else {
            mapOf(
                "Nome:" to nameAccount.toString(),
                "Valor Total:" to price.toString(),
                "Data de pagamento:" to dataFormatada
            )
        }
        DetailsCard(mapDeCategoria = map, onChangeOpenDialog = {textoClicado = it}, id)
    }
}

private fun retornaValorTotalArredondado(valorParcela: Double,totalParcelas: Int): String {
    val valorTotal = valorParcela * totalParcelas
    val totalArredondado = arredondarValor(valorTotal)
    val totalFormatado = formataValorConta(totalArredondado)
    return totalFormatado
}