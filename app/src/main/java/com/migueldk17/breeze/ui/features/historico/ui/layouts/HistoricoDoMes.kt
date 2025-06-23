package com.migueldk17.breeze.ui.features.historico.ui.layouts


import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoItem
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.uistate.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMes(modifier: Modifier,viewModel: HistoricoDoMesViewModel) {

    val contas = viewModel.buscaContasPorMes().collectAsStateWithLifecycle().value
    //Pega as contas já filtradas por data da mais recente a mais antiga
    val historico = viewModel.historico.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)

        GraficoDeBarras(contas, modifier)
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                "Linha do Tempo",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                items(historico) { dia ->
                    //Paga o tamanho da lista
                    val size = historico.indexOf(dia)
                    //Verifica se a conta é a última
                    val isLastItem = size == historico.lastIndex
                    val buscaParcelaPorId = viewModel.buscaParcelaPorId(dia.contaPrincipal.id)
                    val parcela = when(buscaParcelaPorId){
                        is UiState.Empty -> {
                            Log.d(TAG, "HistoricoDoMes: A lista tá vazia")
                            null
                        }
                        is UiState.Loading -> null
                        is UiState.Error -> null
                        is UiState.Success -> {
                            buscaParcelaPorId.data
                        }
                    }


                    HistoricoItem(
                        date = dia.data,
                        nameAccountFirst = dia.contaPrincipal.name,
                        breezeIconFirst = dia.contaPrincipal.icon.toBreezeIconsType(),
                        princeFirst = dia.contaPrincipal.valor,
                        lastIndex = isLastItem,
                        contas = dia.outrasContas,
                        parcela = parcela
                    )

                }
            }
            //Fade do final da lista
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )

        }
    }
    }

