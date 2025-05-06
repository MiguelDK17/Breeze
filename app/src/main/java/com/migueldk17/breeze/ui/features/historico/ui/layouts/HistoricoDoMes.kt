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
fun HistoricoDoMes(modifier: Modifier,viewModel: HistoricoDoMesViewModel) {
    val contas = viewModel.getContasDoMes().collectAsStateWithLifecycle(emptyList()).value
    val historico = viewModel.historico.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier
    ) {
        val modifier = Modifier.size(width = 360.dp, height = 295.dp)
        //GraficoDeBarras(contas, modifier)
        Spacer(modifier = Modifier.height(30.dp))
        /*Row(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                "Linha do Tempo",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )
        }*/
        Spacer(modifier = Modifier.height(15.dp))


        LazyColumn {
            items(historico) { dia ->
                val size = historico.indexOf(dia)
                val isLastItem = size == historico.lastIndex

                HistoricoItem(
                    date = dia.data,
                    nameAccountFirst = dia.contaPrincipal.name,
                    breezeIconFirst = dia.contaPrincipal.icon.toBreezeIconsType(),
                    princeFirst = dia.contaPrincipal.valor,
                    lastIndex = isLastItem,
                    contas = dia.outrasContas
                )
            }

        }
    }
}
