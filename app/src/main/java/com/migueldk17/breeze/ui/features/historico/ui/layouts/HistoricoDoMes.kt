package com.migueldk17.breeze.ui.features.historico.ui.layouts

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migueldk17.breeze.converters.toBreezeIconsType
import com.migueldk17.breeze.converters.toLocalDateTime
import com.migueldk17.breeze.entity.Conta
import com.migueldk17.breeze.ui.features.historico.ui.components.GraficoDeBarras
import com.migueldk17.breeze.ui.features.historico.ui.components.HistoricoItem
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoDoMesViewModel
import com.migueldk17.breeze.ui.features.historico.ui.viewmodels.HistoricoViewModel
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoDoMes(modifier: Modifier,viewModel: HistoricoDoMesViewModel){
    val contas = viewModel.getContasDoMes().collectAsStateWithLifecycle(initialValue = emptyList()).value
    val contasOrdenadas = viewModel.contasOrdenadas.collectAsStateWithLifecycle(initialValue = mapOf()).value

    val contasAgrupadas: Map<LocalDateTime, List<Conta>> = contas.groupBy {
        it.dateTime.toLocalDateTime()
    }

    viewModel.setContasAgrupadas(contasAgrupadas)


    
    
    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)
        GraficoDeBarras(contas, modifier)
        Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text("Linha do Tempo",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp,
                )
            }
        Spacer(modifier = Modifier.height(15.dp))

        val datasVisitadas = mutableSetOf<LocalDate>()
        val primeirasContasPorDia = mutableListOf<Conta>()
        val outrasContas = mutableListOf<Conta>()


        if (contasOrdenadas.isNotEmpty()) {

                for ((_, contasDoMomento) in contasOrdenadas) {
                    for (conta in contasDoMomento) {
                    val data = conta.dateTime.toLocalDateTime().toLocalDate()

                    if (data !in datasVisitadas) {
                        datasVisitadas.add(data)
                        primeirasContasPorDia.add(conta)
                    } else {
                        outrasContas.add(conta)
                    }
                    }
                }
                }
        Log.d(TAG, "HistoricoDoMes: valor de data $datasVisitadas")
        Log.d(TAG, "HistoricoDoMes: valor de conta $primeirasContasPorDia")


        val lista : List<Pair<MutableSet<LocalDate>, MutableList<Conta>>> = listOf(Pair(datasVisitadas, primeirasContasPorDia))

        val datasVisitadasList = datasVisitadas.toList()
        val primeirasContasDoDiaList = primeirasContasPorDia.toList()
        Log.d(TAG, "HistoricoDoMes tamanho da lista: ${datasVisitadasList.size}")
        Log.d(TAG, "HistoricoDoMes tamanho da lista: ${primeirasContasDoDiaList.size}")
        Log.d(TAG, "HistoricoDoMes: as outras contas: $outrasContas")


                LazyColumn {
                    items(primeirasContasDoDiaList) { conta ->

                        val dataContaPrimeira = conta.dateTime.toLocalDateTime().toLocalDate()

                        val outrasContasDoMesmoDia = outrasContas.filter { outraConta ->
                            outraConta.dateTime.toLocalDateTime().toLocalDate() == dataContaPrimeira
                        }
                            HistoricoItem(
                                date = conta.dateTime.toLocalDateTime().toLocalDate(),
                                nameAccountFirst = conta.name,
                                breezeIconFirst = conta.icon.toBreezeIconsType(),
                                princeFirst = conta.valor,
                                contas = outrasContasDoMesmoDia
                            )

                    }

                }

            }
}

